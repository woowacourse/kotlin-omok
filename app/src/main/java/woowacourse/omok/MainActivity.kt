package woowacourse.omok

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import domain.OmokBoard
import domain.OmokGameListener
import domain.State
import domain.Stone

class MainActivity : AppCompatActivity() {

    private val omokDatabase by lazy { OmokDatabase(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val board = findViewById<TableLayout>(R.id.board)

        val omokUiBoard = board
            .children
            .filterIsInstance<TableRow>()
            .flatMap { it.children }
            .filterIsInstance<ImageView>()
            .toList()

        val omokGameListener = object : OmokGameListener {

            override fun onMove(omokBoard: OmokBoard, state: State, stone: Stone) {
                val stoneImage = when (state) {
                    State.BLACK -> R.drawable.white_stone
                    State.WHITE -> R.drawable.black_stone
                    State.EMPTY -> null
                }

                stoneImage?.let {
                    omokUiBoard[stone.row * BOARD_SIZE + stone.column].setImageResource(it)
                }
            }

            override fun onMoveFail() {
                Toast.makeText(this@MainActivity, "중복된 위치입니다.", Toast.LENGTH_SHORT).show()
            }

            override fun onForbidden() {
                Toast.makeText(this@MainActivity, "금수 입니다.", Toast.LENGTH_SHORT).show()
            }

            override fun onFinish(state: State) {
                moveToGameOver(state)
            }
        }

        val omokController = OmokController(
            omokGameListener, omokDatabase, omokUiBoard
        )

        omokUiBoard.forEachIndexed { index, view ->
            view.setOnClickListener {
                omokController.run(index)
            }
        }
    }

    private fun moveToGameOver(state: State) {
        val intent = Intent(this, GameOverActivity::class.java).apply {
            putExtra(OMOK_WINNER, state.name)
        }
        startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        omokDatabase.close()
    }

    companion object {
        private const val BOARD_SIZE = 15
        const val OMOK_WINNER = "OMOK_WINNER"
    }
}
