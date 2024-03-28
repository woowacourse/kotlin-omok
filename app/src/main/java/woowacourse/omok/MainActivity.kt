package woowacourse.omok

import android.os.Bundle
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import woowacourse.omok.domain.omok.model.Board
import woowacourse.omok.domain.omok.model.Color
import woowacourse.omok.domain.omok.model.GameResult
import woowacourse.omok.domain.omok.model.Position

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        playUntilFinish()
    }

    private fun playUntilFinish() {
        val board = findViewById<TableLayout>(R.id.board)
        val androidBoard = Board()
        val explainMessage = findViewById<TextView>(R.id.expalin_message)
        explainMessage.text = "흑의 차례입니다"
        board
            .children
            .filterIsInstance<TableRow>()
            .forEachIndexed { rowIndex, tableRow ->
                tableRow.children
                    .filterIsInstance<ImageView>()
                    .forEachIndexed { colIndex, imageView ->
                        imageView.setOnClickListener {
                            runCatching {
                                val eachPlacedPosition =
                                    Position.of(rowIndex + 1, colIndex.toChar() + 'A'.code)
                                androidBoard.place(eachPlacedPosition)
                                when (androidBoard.lastTurn) {
                                    Color.BLACK -> imageView.setImageResource(R.drawable.black_stone)
                                    Color.WHITE -> imageView.setImageResource(R.drawable.white_stone)
                                    Color.NONE -> return@setOnClickListener
                                }
                                if (androidBoard.getGameResult(eachPlacedPosition) != GameResult.PROCEEDING) {
                                    explainMessage.text =
                                        "${androidBoard.getGameResult(eachPlacedPosition).label}의 승리"
                                    return@setOnClickListener
                                }
                                explainMessage.text = androidBoard.currentTurn.label + "의 차례입니다"
                            }.onFailure {
                                explainMessage.text = it.message
                            }
                        }
                    }
            }
    }
}
