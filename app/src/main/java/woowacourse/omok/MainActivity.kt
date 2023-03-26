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
import omok.model.game.Judgement
import omok.model.state.ForbiddenFour
import omok.model.state.ForbiddenThree
import omok.model.state.Stay
import omok.model.state.Win
import omok.model.stone.Coordinate
import omok.model.stone.GoStoneColor
import omok.view.toKorean
import woowacourse.omok.db.OmokDB

class MainActivity : AppCompatActivity() {

    private var controller = OmokController()
    private val db by lazy { OmokDB(this) }
    private var isRunning = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val boardUi = findViewById<TableLayout>(R.id.board)
        val board = boardUi
            .children
            .filterIsInstance<TableRow>()
            .flatMap { it.children }
            .filterIsInstance<ImageView>()
            .toList()

        board.forEachIndexed { index, view ->
            view.setOnClickListener { updateBoard(view, index) }
        }

        val restartButton = findViewById<Button>(R.id.button_restart)
        restartButton.setOnClickListener {
            recreate()
            db.clear()
        }

        setPreviousGame(board)
    }

    private fun updateBoard(view: ImageView, index: Int) {
        if (!isRunning) {
            makeMessage("게임이 끝났습니다!")
            return
        }

        val row = index / 15
        val col = index % 15

        val coordinate = Coordinate(col, 14 - row)
        if (!controller.board.canAdd(coordinate)) {
            makeMessage("이미 해당 위치에 돌이 있어요!")
            return
        }

        val state = controller.playTurn(coordinate)
        if (!state.isForbidden) {
            setStoneImage(view, controller.board.lastPlacedStone?.color)
            db.insert(controller.board.lastPlacedStone)
        }

        when (state) {
            is Win -> {
                makeMessage("${controller.board.lastPlacedStone?.color?.toKorean()}이 승리했습니다!")
                isRunning = false
            }
            is ForbiddenThree -> makeMessage("돌을 놓을 수 없어요! (3-3)")
            is ForbiddenFour -> makeMessage("돌을 놓을 수 없어요! (4-4)")
            is Stay -> {}
        }
    }

    private fun setPreviousGame(board: List<ImageView>) {
        val stones = db.getExistingStones()
        controller.addAll(stones)

        stones.forEach {
            val index = (14 - it.coordinate.y) * 15 + it.coordinate.x
            setStoneImage(board[index], it.color)
        }

        controller.board.lastPlacedStone.apply {
            if (this != null) {
                isRunning = Judgement.judge(controller.board, this) !is Win
            }
        }
    }

    private fun setStoneImage(view: ImageView, color: GoStoneColor?) {
        when (color) {
            GoStoneColor.BLACK, null -> view.setImageResource(R.drawable.black_stone)
            GoStoneColor.WHITE -> view.setImageResource(R.drawable.white_stone)
        }
    }

    private fun makeMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
