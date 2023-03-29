package woowacourse.omok

import android.os.Bundle
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import domain.Board
import domain.OmokGame
import domain.event.*
import domain.stone.Point
import domain.stone.Stone
import domain.stone.Team
import woowacourse.omok.repository.StoneDbHelper
import woowacourse.omok.repository.StoneRepository

class MainActivity : AppCompatActivity(), CreateEventListener, PlaceStoneEventListener, FinishEventListener {

    private val stoneRepository: StoneRepository by lazy { StoneRepository(StoneDbHelper(this).writableDatabase) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val omokGame = createOmokGame()
        initBoardViewClickListener(omokGame)
    }

    private fun createOmokGame(): OmokGame {
        val createEventManager = CreateEventManager()
        createEventManager.add(this)
        val placeStoneEventManager = PlaceStoneEventManager()
        placeStoneEventManager.add(this)
        val finishEventManager = FinishEventManager()
        finishEventManager.add(this)

        return OmokGame(
            createEventManager = createEventManager,
            placeStoneEventManager = placeStoneEventManager,
            finishEventManager = finishEventManager,
        )
    }

    private fun initBoardViewClickListener(omokGame: OmokGame) {
        getBoardView().forEachPointed { point, imageView ->
            val stone = Stone(point)

            imageView.setOnClickListener { if (omokGame.canPlace(stone)) omokGame.place(stone) }
        }
    }

    override fun notifyCreateEventHasOccurred(omokGame: OmokGame) {
        val stones = stoneRepository.findAll()
        stoneRepository.deleteAll()
        stones.forEach { omokGame.place(it) }
    }

    override fun notifyPlaceStoneEventHasOccurred(omokGame: OmokGame) {
        drawRunningBoardView(omokGame)
        stoneRepository.insert(Stone(omokGame.lastPoint!!))
    }

    private fun drawRunningBoardView(omokGame: OmokGame) {
        getBoardView().forEachPointed { point, view ->
            view.setImageResource(point.getResourceForRunningGame(omokGame))
        }
    }

    private fun getBoardView(): List<ImageView> {
        val board = findViewById<TableLayout>(R.id.board)
        return board
            .children
            .filterIsInstance<TableRow>()
            .flatMap { it.children }
            .filterIsInstance<ImageView>()
            .toList()
    }

    private fun List<ImageView>.forEachPointed(action: (Point, ImageView) -> Unit) {
        this.forEachIndexed { index, imageView ->
            val row = index % Board.BOARD_SIZE + 1
            val col = Board.BOARD_SIZE - index / Board.BOARD_SIZE

            action(Point(row, col), imageView)
        }
    }

    override fun notifyFinishEventHasOccurred(omokGame: OmokGame) {
        drawFinalBoardView(omokGame)
        NonDelayToast.show(this, "%s의 승리입니다.".format(omokGame.getWinner().toKorean()))
        getBoardView().forEach { it.setOnClickListener { } }
        stoneRepository.deleteAll()
    }

    private fun Team.toKorean(): String =
        when (this) {
            Team.BLACK -> "흑"
            Team.WHITE -> "백"
        }

    private fun drawFinalBoardView(omokGame: OmokGame) {
        getBoardView().forEachPointed { point, view ->
            view.setImageResource(point.getResourceForFinishedGame(omokGame))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        stoneRepository.close()
    }
}
