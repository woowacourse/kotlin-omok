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
    private var turn = State.BLACK
    private var isOver = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val board = findViewById<TableLayout>(R.id.board)
        val eachBoard = board
            .children
            .filterIsInstance<TableRow>()
            .map { it.children.filterIsInstance<ImageView>().toList() }
            .toList()

        val omokGame = OmokGame(omokGameListener = OmokGameListener())

        eachBoard.forEachIndexed { row, imageViews ->
            imageViews.forEachIndexed { column, imageView ->
                imageView.setOnClickListener {
                    Log.d(TAG_STONE_POSITION, "${row}행 ${column}열")
                    when (turn) {
                        State.BLACK -> {
                            if (!isOver && omokGame.successTurn(Stone(row, column), State.BLACK)) {
                                eachBoard[row][column].setImageResource(R.drawable.black_stone)
                                if (omokGame.isVictory(State.BLACK)) isOver = true
                                turn = State.WHITE
                            }
                        }
                        State.WHITE -> {
                            if (!isOver && omokGame.successTurn(Stone(row, column), State.WHITE)) {
                                eachBoard[row][column].setImageResource(R.drawable.white_stone)
                                if (omokGame.isVictory(State.WHITE)) isOver = true
                                turn = State.BLACK
                            }
                        }
                        else -> null
                    }
                }
            }
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
            Toast.makeText(this@MainActivity, "그곳은 둘 수 없습니다.", Toast.LENGTH_LONG).show()
        }

        override fun onForbidden() {
            Toast.makeText(this@MainActivity, "그곳은 금수 입니다.", Toast.LENGTH_LONG).show()
        }

        override fun onFinish(state: State): State {
            Toast.makeText(this@MainActivity, "승!", Toast.LENGTH_LONG).show()
            return state
        }
    }
}
