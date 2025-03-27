package woowacourse.omok

import android.os.Bundle
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.children
import woowacourse.omok.domain.OmokBoard
import woowacourse.omok.domain.OmokGame
import woowacourse.omok.domain.Point
import woowacourse.omok.domain.stone.StoneColor

class MainActivity : AppCompatActivity() {
    private val omokGame = OmokGame(OmokBoard())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val board = findViewById<TableLayout>(R.id.board)
        board
            .children
            .filterIsInstance<TableRow>()
            .toList()
            .reversed()
            .forEachIndexed { rowIndex, row ->
                row.children
                    .filterIsInstance<ImageView>()
                    .forEachIndexed { colIndex, view ->
                        view.tag = Point(rowIndex, colIndex)
                        view.setOnClickListener {
                            playGame(view)
                        }
                    }
            }
    }

    private fun playGame(view: ImageView) {
        omokGame.play(
            onTurn = { _, _ -> },
            onPointSelected = { view.tag as Point },
            onForbiddenMove = { message ->
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            },
            onStonePlaced = { stone ->
                when (stone.color) {
                    StoneColor.BLACK -> view.setImageResource(R.drawable.black_stone)
                    StoneColor.WHITE -> view.setImageResource(R.drawable.white_stone)
                }
            },
        )
        omokGame.finish { Toast.makeText(this, "$it", Toast.LENGTH_SHORT).show() }
    }
}
