package woowacourse.omok

import android.os.Bundle
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import omok.controller.OmokController
import omok.model.state.State
import omok.model.stone.Coordinate
import omok.model.stone.GoStoneColor
import omok.view.toKorean

class MainActivity : AppCompatActivity() {

    private val controller = OmokController()
    private var isRunning = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val boardLayout = findViewById<TableLayout>(R.id.board)
        val board = boardLayout
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
        if (!isRunning) {
            makeMessage("게임이 끝났습니다!")
            return
        }

        val row = index / 15
        val col = index % 15

        val coordinate = Coordinate(col, 15 - row)
        if (!controller.board.canAdd(coordinate)) {
            makeMessage("이미 해당 위치에 돌이 있어요!")
            return
        }

        val state = controller.playTurn(coordinate)
        if (state == State.Win || state == State.Stay) setStoneImage(view)
        when (state) {
            State.Win -> {
                makeMessage("${controller.board.lastPlacedStone?.color?.toKorean()}이 승리했습니다!")
                isRunning = false
            }
            State.DoubleThree -> makeMessage("돌을 놓을 수 없어요! (3-3)")
            State.DoubleFour -> makeMessage("돌을 놓을 수 없어요! (4-4)")
            State.Stay -> {}
        }
    }

    private fun setStoneImage(view: ImageView) {
        when (controller.board.lastPlacedStone?.color) {
            GoStoneColor.BLACK, null -> view.setImageResource(R.drawable.black_stone)
            GoStoneColor.WHITE -> view.setImageResource(R.drawable.white_stone)
        }
    }

    private fun makeMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
