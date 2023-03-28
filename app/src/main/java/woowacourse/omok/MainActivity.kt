package woowacourse.omok

import android.os.Bundle
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import domain.OmokGame
import domain.event.*
import domain.stone.Point
import domain.stone.Stone
import woowacourse.omok.repository.StoneRepository

class MainActivity : AppCompatActivity(), PlaceStoneEventListener, FinishEventListener {

    private val stoneRepository: StoneRepository by lazy { StoneRepository(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val omokGame = createOmokGame()
        initBoardViewClickListener(omokGame)
    }

    private fun createOmokGame(): OmokGame {
        val placeStoneEventManager = PlaceStoneEventManager()
        placeStoneEventManager.add(this)
        val finishEventManager = FinishEventManager()
        finishEventManager.add(this)

        val omokGame = OmokGame(
            placeStoneEventManager = placeStoneEventManager,
            finishEventManager = finishEventManager
        )

        val stones = stoneRepository.readAll()
        stoneRepository.deleteAll()
        stones.forEach { omokGame.place(it) }

        return omokGame
    }

    private fun initBoardViewClickListener(omokGame: OmokGame) {
        getBoardView().forEachPointed { point, imageView ->
            val stone = Stone(point)

            imageView.setOnClickListener { if (omokGame.canPlace(stone)) omokGame.place(stone) }
        }
    }

    override fun notifyPlaceStoneEventHasOccurred(omokGame: OmokGame) {
        updateBoardView(omokGame)
        stoneRepository.addStone(Stone(omokGame.getPointOfLastStonePlaced()!!))
        println(omokGame.getPointOfLastStonePlaced())
    }

    private fun updateBoardView(omokGame: OmokGame) {
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
            val row = index % OmokGame.BOARD_SIZE + 1
            val col = OmokGame.BOARD_SIZE - index / OmokGame.BOARD_SIZE

            action(Point(row, col), imageView)
        }
    }

    override fun notifyFinishEventHasOccurred(omokGame: OmokGame) {
        drawFinalBoardView(omokGame)
        NonDelayToast.show(this, "%s의 승리입니다.".format(if (omokGame.isBlackWin()) "흑" else "백"))
        getBoardView().forEach { it.setOnClickListener { } }
        stoneRepository.deleteAll()
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
