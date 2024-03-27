package woowacourse.omok

import android.os.Bundle
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import woowacourse.omok.domain.model.BlackTurn
import woowacourse.omok.domain.model.Board
import woowacourse.omok.domain.model.FinishedTurn
import woowacourse.omok.domain.model.Point
import woowacourse.omok.domain.model.Point.Companion.MESSAGE_INVALID_POINT_INPUT
import woowacourse.omok.domain.model.RuleAdapter
import woowacourse.omok.domain.model.StoneType
import woowacourse.omok.domain.model.Turn

class MainActivity : AppCompatActivity() {
    private val board: Board = Board(15)
    private var turn: Turn = BlackTurn()
    private val ruleAdapter = RuleAdapter(board)
    private var onGame = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tableLayoutBoard: TableLayout = findViewById(R.id.board)
        tableLayoutBoard
            .children
            .filterIsInstance<TableRow>()
            .forEachIndexed { x, it ->
                it.children.filterIsInstance<ImageView>()
                    .forEachIndexed { y, view ->
                        view.setOnClickListener {
                            if (!onGame) return@setOnClickListener

                            val nextTurn = board.putStone(Point(x, y), turn, ruleAdapter)
                            if (turn != nextTurn) {
                                view.setImageResource(getStoneImage(turn.stoneType))
                                turn = nextTurn
                            } else {
                                Toast.makeText(
                                    this,
                                    MESSAGE_INVALID_POINT_INPUT,
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                            if (turn is FinishedTurn) {
                                Toast.makeText(this, turn.getWinner(), Toast.LENGTH_SHORT).show()
                                onGame = false
                            }
                        }
                    }
            }
    }

    private fun getStoneImage(stoneType: StoneType): Int {
        return when (stoneType) {
            StoneType.BLACK -> R.drawable.black_stone
            StoneType.WHITE -> R.drawable.white_stone
            StoneType.EMPTY -> 0
        }
    }
}
