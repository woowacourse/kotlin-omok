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

        lateinit var eachPlacedPosition: Position
        val board = findViewById<TableLayout>(R.id.board)
        val androidBoard = Board()
        val explainMessage = findViewById<TextView>(R.id.expalin_message)
        board
            .children
            .filterIsInstance<TableRow>()
            .forEachIndexed { rowIndex, tableRow ->
                tableRow.children
                    .filterIsInstance<ImageView>()
                    .forEachIndexed { colIndex, imageView ->
                        imageView.setOnClickListener {
                            when (androidBoard.turn) {
                                Color.BLACK -> imageView.setImageResource(R.drawable.black_stone)
                                Color.WHITE -> imageView.setImageResource(R.drawable.white_stone)
                                Color.NONE -> return@setOnClickListener
                            }
                            eachPlacedPosition =
                                Position.of(rowIndex + 1, colIndex.toChar() + 'A'.code)
                            androidBoard.place(eachPlacedPosition)
                            if (androidBoard.getGameResult(eachPlacedPosition) != GameResult.PROCEEDING) {
                                explainMessage.text = "게임끝"
                            }
                        }
                    }
            }
    }
}
