package woowacourse.omok.game

import android.os.Bundle
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import com.google.android.material.snackbar.Snackbar
import woowacourse.omok.R
import woowacourse.omok.game.database.OmokEntry
import woowacourse.omok.game.database.OmokEntryDao
import woowacourse.omok.game.model.Board
import woowacourse.omok.game.model.Color
import woowacourse.omok.game.model.Point
import woowacourse.omok.game.model.RenjuRuleAdapter
import woowacourse.omok.game.model.Stone
import woowacourse.omok.game.model.Turn
import woowacourse.omok.game.model.isBlack

class MainActivity : AppCompatActivity() {
    private val logicBoard = Board(15, RenjuRuleAdapter())
    private lateinit var board: TableLayout
    private val dao by lazy { OmokEntryDao(this) }
    private lateinit var turn: Turn

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        board = findViewById(R.id.board)

        fetchSavedData()
        turn = Turn(logicBoard.lastColor()).currentTurn()

        startGame()
    }

    private fun startGame() {
        board
            .children
            .filterIsInstance<TableRow>()
            .flatMap { it.children }
            .filterIsInstance<ImageView>()
            .forEachIndexed { index, view ->
                play(index, view)
            }
    }

    private fun play(
        index: Int,
        view: ImageView,
    ) {
        view.setOnClickListener {
            validatePlaceable(currentStone(index), view)
        }
    }

    private fun currentStone(index: Int): Stone = Stone(Point.from(index), turn.color())

    private fun validatePlaceable(
        stone: Stone,
        view: ImageView,
    ) {
        if (placeable(stone)) {
            putStone(stone, view)
        } else {
            handleInvalidPoint(stone, view)
        }
    }

    private fun placeable(stone: Stone): Boolean = logicBoard.pointEmpty(stone.point) && logicBoard.isValid(stone)

    private fun putStone(
        stone: Stone,
        view: ImageView,
    ) {
        view.setImageResource(imageResource(stone))
        logicBoard.add(stone)
        saveStoneData(stone)
        resetBanned()
        checkEndState()
        turn.next()
    }

    private fun resetBanned() {
        while (logicBoard.bannedPointCount() > 0) {
            resetOneBannedPoint()
        }
    }

    private fun resetOneBannedPoint() {
        val currentPoint = logicBoard.fetchBannedPoint()
        board
            .children
            .filterIsInstance<TableRow>()
            .flatMap { it.children }
            .filterIsInstance<ImageView>()
            .filterIndexed { index, _ ->
                index == currentPoint.index()
            }.forEach { imageView ->
                imageView.setImageDrawable(null)
            }
    }

    private fun checkEndState() {
        if (logicBoard.checkEndCondition()) {
            showSnackBarAndReset()
        }
    }

    private fun showSnackBarAndReset() {
        popUpSnackBar(turn)
    }

    private fun popUpSnackBar(turn: Turn) {
        val snackBar =
            Snackbar.make(
                board,
                "게임 종료!  ${turnToString(turn)}의 승리입니다!\n확인을 누르면 게임이 다시 시작됩니다.",
                Snackbar.LENGTH_INDEFINITE,
            )
        snackBar.setAction("확인") {
            resetData()
            snackBar.dismiss()
        }
        snackBar.show()
    }

    private fun resetData() {
        resetBoardView()
        logicBoard.reset()
        deleteDbData()
        turn.reset()
    }

    private fun resetBoardView() {
        board
            .children
            .filterIsInstance<TableRow>()
            .flatMap { it.children }
            .filterIsInstance<ImageView>()
            .forEach { view ->
                view.setImageDrawable(null)
            }
    }

    private fun handleInvalidPoint(
        stone: Stone,
        view: ImageView,
    ) {
        if (!logicBoard.isValid(stone)) {
            view.setImageResource(R.drawable.unavailable_point)
            logicBoard.addBannedPoint(stone.point)
            Toast.makeText(this, "둘 수 없는 자리입니다.", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "둘 수 없는 자리입니다.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun imageResource(turn: Turn): Int {
        return if (turn.isBlack()) {
            R.drawable.black_stone
        } else {
            R.drawable.white_stone
        }
    }

    private fun imageResource(stone: Stone): Int {
        return if (stone.color.isBlack()) {
            R.drawable.black_stone
        } else {
            R.drawable.white_stone
        }
    }

    private fun turnToString(turn: Turn): String = colorToString(turn.color())

    private fun colorToString(color: Color): String = if (color == Color.BLACK) "흑" else "백"

    private fun saveStoneData(stone: Stone) {
        dao.save(OmokEntry(stone.point.index(), colorToString(stone.color)))
    }

    private fun fetchSavedData() {
        val data = dao.findAll()
        setUpLogicBoard(data)
    }

    private fun setUpLogicBoard(omokData: List<OmokEntry>) {
        omokData.forEach { it ->
            val color = stringToColor(it.color)
            logicBoard.add(Stone(Point.from(it.index), color))

            val currentIndex = it.index
            board.children
                .filterIsInstance<TableRow>()
                .flatMap { it.children }
                .filterIsInstance<ImageView>()
                .forEachIndexed { index, imageView ->
                    if (index == currentIndex) {
                        imageView.setImageResource(imageResource(Turn(color)))
                    }
                }
        }
    }

    private fun stringToColor(color: String): Color = if (color == "흑") Color.BLACK else Color.WHITE

    private fun deleteDbData() {
        dao.drop()
    }
}
