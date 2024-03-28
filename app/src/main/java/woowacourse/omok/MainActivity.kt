package woowacourse.omok

import android.os.Bundle
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import omok.model.OmokGame
import omok.model.Point
import omok.model.StoneType
import omok.model.Point.Companion.MESSAGE_INVALID_POINT_INPUT

class MainActivity : AppCompatActivity() {
    private val omokGame = OmokGame()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val boardUi = findViewById<TableLayout>(R.id.board)
        boardUi
            .children
            .filterIsInstance<TableRow>()
            .forEachIndexed { x, row ->
                row.children.filterIsInstance<ImageView>()
                    .forEachIndexed { y, view ->
                        view.setOnClickListener {
                            if (omokGame.isGameFinished()) {
                                Toast.makeText(this, "게임이 종료 되었습니다.", Toast.LENGTH_SHORT).show()
                                return@setOnClickListener
                            }
                            progressGameTurn(view, x, y)
                        }
                    }
            }
    }

    private fun progressGameTurn(view: ImageView, x: Int, y: Int) {
        val isSuccess = omokGame.tryPlayTurn(
            updateTurn = { view.setImageResource(getStoneImage(it.before?.type)) },
            getPoint = { Point(x, y) }
        )
        if (!isSuccess) Toast.makeText(this, MESSAGE_INVALID_POINT_INPUT, Toast.LENGTH_SHORT).show()
    }

    private fun getStoneImage(stoneType: StoneType?): Int {
        return when (stoneType) {
            StoneType.BLACK -> R.drawable.black_stone
            StoneType.WHITE -> R.drawable.white_stone
            StoneType.EMPTY -> 0
            null -> 0
        }
    }
}
