package woowacourse.omok

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import domain.OmokBoard
import domain.OmokGame
import domain.State
import domain.Stone
import domain.listener.OmokListener
import view.OutputView

class MainActivity : AppCompatActivity() {
    private val TAG_STONE_POSITION = "착수 위치"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val board = findViewById<TableLayout>(R.id.board)
        val boardView = board
            .children
            .filterIsInstance<TableRow>()
            .map { it.children.filterIsInstance<ImageView>().toList() }
            .toList()

        val omokGame = OmokGame(omokGameListener = OmokGameListener())

        boardView.forEachIndexed { row, imageViews ->
            imageViews.forEachIndexed { column, imageView ->
                imageView.setOnClickListener {
                    Log.d(TAG_STONE_POSITION, "${row}행 ${column}열")
                    if (omokGame.successTurn(Stone(row, column))) {
                        changeImage(omokGame, boardView[row][column])
                    }
                }
            }
        }
    }

    private fun changeImage(omokGame: OmokGame, boardView: ImageView) {
        when (omokGame.turn.nextState()) {
            State.BLACK -> boardView.setImageResource(R.drawable.black_stone)
            State.WHITE -> boardView.setImageResource(R.drawable.white_stone)
            else -> null
        }
    }

    inner class OmokGameListener() : OmokListener {
        override fun onStoneRequest(): Stone {
            TODO("Not yet implemented")
        }

        override fun onMove(omokBoard: OmokBoard, state: State, stone: Stone) {
            OutputView().printOmokState(omokBoard, state, stone)
        }

        override fun onMoveFail() {
            Toast.makeText(this@MainActivity, "이미 돌이 존재합니다.", Toast.LENGTH_SHORT).show()
        }

        override fun onForbidden() {
            Toast.makeText(this@MainActivity, "그곳은 금수 입니다.", Toast.LENGTH_SHORT).show()
        }

        override fun onFinish(state: State): State {
            Toast.makeText(this@MainActivity, "${state.name}승!", Toast.LENGTH_SHORT).show()
            return state
        }
    }
}
