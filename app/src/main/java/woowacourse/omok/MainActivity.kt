package woowacourse.omok

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import omok.controller.OmokController
import omok.model.state.ForbiddenFour
import omok.model.state.ForbiddenThree
import omok.model.state.Stay
import omok.model.state.Win
import omok.model.stone.Coordinate
import omok.model.stone.GoStoneColor
import woowacourse.omok.db.OmokDB
import woowacourse.omok.db.OmokRepository

class MainActivity : AppCompatActivity() {

    private val controller = OmokController()
    private val db by lazy { OmokDB(OmokRepository(this)) }
    private lateinit var board: List<ImageView>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setBoard()
        setRestartButton()
        setPreviousGame()
    }

    private fun setRestartButton() {
        val restartButton = findViewById<Button>(R.id.button_restart)
        restartButton.setOnClickListener {
            recreate()
            db.clear()
        }
    }

    private fun setBoard() {
        val boardUi = findViewById<TableLayout>(R.id.board)
        board = boardUi
            .children
            .filterIsInstance<TableRow>()
            .flatMap { it.children }
            .filterIsInstance<ImageView>()
            .toList()

        board.forEachIndexed { index, view ->
            view.setOnClickListener { updateBoard(view, index) }
        }
    }

    private fun updateBoard(view: ImageView, index: Int) {
        if (!controller.isGameRunning) {
            makeMessage("게임이 끝났습니다!")
            return
        }

        val coordinate = Coordinate.of(index)
        if (isStoneExisted(coordinate)) {
            makeMessage("이미 해당 위치에 돌이 있어요!")
            return
        }

        addStone(coordinate, view)
    }

    private fun addStone(coordinate: Coordinate, view: ImageView) {
        val state = controller.playTurn(coordinate)
        if (!state.isForbidden) {
            setStoneImage(view, controller.getLastTurnColor())
            db.insert(controller.getLastPlacedStone())
        }

        when (state) {
            is Win -> makeMessage("${controller.getLastTurnColor().toKorean()}이 승리했습니다!")
            is ForbiddenThree -> makeMessage("돌을 놓을 수 없어요! (3-3)")
            is ForbiddenFour -> makeMessage("돌을 놓을 수 없어요! (4-4)")
            is Stay -> {}
        }
    }

    private fun isStoneExisted(coordinate: Coordinate): Boolean = !controller.board.canAdd(coordinate)

    private fun setPreviousGame() {
        val stones = db.getExistingStones()
        controller.addAll(stones)

        stones.forEach {
            val index = (14 - it.coordinate.y) * 15 + it.coordinate.x
            setStoneImage(board[index], it.color)
        }
    }

    private fun setStoneImage(view: ImageView, color: GoStoneColor) {
        when (color) {
            GoStoneColor.BLACK -> view.setImageResource(R.drawable.black_stone)
            GoStoneColor.WHITE -> view.setImageResource(R.drawable.white_stone)
        }
    }

    private fun makeMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun GoStoneColor.toKorean(): String = when (this) {
        GoStoneColor.BLACK -> "흑돌"
        GoStoneColor.WHITE -> "백돌"
    }
}
