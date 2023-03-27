package woowacourse.omok

import android.os.Bundle
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import domain.OmokGame
import domain.event.*
import domain.stone.Point
import domain.stone.Stone
import woowacourse.omok.repository.StoneRepository

class MainActivity : AppCompatActivity(), PlaceStoneEventListener, FinishEventListener {

    private var toast: Toast? = null
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

        stoneRepository.readAll().forEach { omokGame.place(it) }

        return omokGame
    }

    private fun initBoardViewClickListener(omokGame: OmokGame) {
        getBoardView().forEachPointed { point, imageView ->
            val stone = Stone(point)

            imageView.setOnClickListener { if (omokGame.canPlace(stone)) omokGame.place(stone) }
        }
    }

    private fun showToast(text: String) {
        toast?.cancel()
        toast = Toast.makeText(this, text, Toast.LENGTH_SHORT)
        toast?.show()
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

    private fun Point.getResourceForRunningGame(omokGame: OmokGame): Int =
        when {
            this.isWhereBlackStoneIsPlaced(omokGame) -> R.drawable.black_stone
            this.isWhereWhiteStoneIsPlaced(omokGame) -> R.drawable.white_stone
            this.isWhereNoStoneCanBePlaced(omokGame) -> R.drawable.not_place_mark
            this.isLeftTopCornerOfBoard() -> R.drawable.board_top_left
            this.isRightTopCornerOfBoard() -> R.drawable.board_top_right
            this.isLeftBottomCornerOfBoard() -> R.drawable.board_bottom_left
            this.isRightBottomCornerOfBoard() -> R.drawable.board_bottom_right
            this.isTopCornerOfBoard() -> R.drawable.board_top
            this.isBottomCornerOfBoard() -> R.drawable.board_bottom
            this.isLeftCornerOfBoard() -> R.drawable.board_left
            this.isRightCornerOfBoard() -> R.drawable.board_right
            else -> R.drawable.board_center
        }

    private fun Point.isWhereBlackStoneIsPlaced(omokGame: OmokGame): Boolean =
        omokGame.blackStoneIsPlaced(Stone(this))

    private fun Point.isWhereWhiteStoneIsPlaced(omokGame: OmokGame): Boolean =
        omokGame.whiteStoneIsPlaced(Stone(this))

    private fun Point.isWhereNoStoneCanBePlaced(omokGame: OmokGame): Boolean =
        omokGame.canPlace(Stone(this)).not()

    private fun Point.isLeftTopCornerOfBoard(): Boolean = x == 'A' && y == OmokGame.BOARD_SIZE
    private fun Point.isRightTopCornerOfBoard(): Boolean =
        x == 'A' + OmokGame.BOARD_SIZE - 1 && y == OmokGame.BOARD_SIZE

    private fun Point.isLeftBottomCornerOfBoard(): Boolean = x == 'A' && y == 1
    private fun Point.isRightBottomCornerOfBoard(): Boolean =
        x == 'A' + OmokGame.BOARD_SIZE - 1 && y == 1

    private fun Point.isTopCornerOfBoard(): Boolean = y == OmokGame.BOARD_SIZE
    private fun Point.isBottomCornerOfBoard(): Boolean = y == 1
    private fun Point.isLeftCornerOfBoard(): Boolean = x == 'A'
    private fun Point.isRightCornerOfBoard(): Boolean = x == 'A' + OmokGame.BOARD_SIZE - 1

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
        showToast("%s의 승리입니다.".format(if (omokGame.isBlackWin()) "흑" else "백"))
        getBoardView().forEach { it.setOnClickListener { } }
        stoneRepository.deleteAll()
    }

    private fun drawFinalBoardView(omokGame: OmokGame) {
        getBoardView().forEachPointed { point, view ->
            view.setImageResource(point.getResourceForFinishedGame(omokGame))
        }
    }

    private fun Point.getResourceForFinishedGame(omokGame: OmokGame): Int =
        when {
            this.isWhereBlackStoneIsPlaced(omokGame) -> R.drawable.black_stone
            this.isWhereWhiteStoneIsPlaced(omokGame) -> R.drawable.white_stone
            this.isLeftTopCornerOfBoard() -> R.drawable.board_top_left
            this.isRightTopCornerOfBoard() -> R.drawable.board_top_right
            this.isLeftBottomCornerOfBoard() -> R.drawable.board_bottom_left
            this.isRightBottomCornerOfBoard() -> R.drawable.board_bottom_right
            this.isTopCornerOfBoard() -> R.drawable.board_top
            this.isBottomCornerOfBoard() -> R.drawable.board_bottom
            this.isLeftCornerOfBoard() -> R.drawable.board_left
            this.isRightCornerOfBoard() -> R.drawable.board_right
            else -> R.drawable.board_center
        }
}
