package woowacourse.omok

import android.os.Bundle
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import domain.OmokBoard
import domain.OmokGame
import domain.OmokGameListener
import domain.State
import domain.Stone

class MainActivity : AppCompatActivity() {

    var isBlackTurn = true
    var isFinish = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val board = findViewById<TableLayout>(R.id.board)

        val omokBoard = board
            .children
            .filterIsInstance<TableRow>()
            .flatMap { it.children }
            .filterIsInstance<ImageView>()
            .toList()

        val omokGameListener = object : OmokGameListener {

            override fun onMove(omokBoard: OmokBoard, state: State, stone: Stone) {}

            override fun onMoveFail() {
                Toast.makeText(this@MainActivity, "중복된 위치입니다.", Toast.LENGTH_SHORT).show()
            }

            override fun onForbidden() {
                Toast.makeText(this@MainActivity, "금수 입니다.", Toast.LENGTH_SHORT).show()
            }

            override fun onFinish(state: State) {
                Toast.makeText(this@MainActivity, "${state.name} 승", Toast.LENGTH_SHORT).show()
            }
        }

        val omokGame = OmokGame(omokGameListener = omokGameListener)

        omokBoard.forEachIndexed { index, view ->
            val row = index / BOARD_SIZE
            val col = index % BOARD_SIZE
            view.setOnClickListener {
                val state = if (isBlackTurn) State.BLACK else State.WHITE
                val stoneImage = if (isBlackTurn) R.drawable.black_stone else R.drawable.white_stone

                if (!isFinish && omokGame.successTurn(Stone(row, col), state)) {
                    view.setImageResource(stoneImage)
                    isBlackTurn = !isBlackTurn
                    if (omokGame.isVictory(state)) isFinish = true
                }
            }
        }
    }

    companion object {
        private const val BOARD_SIZE = 15
    }
}
