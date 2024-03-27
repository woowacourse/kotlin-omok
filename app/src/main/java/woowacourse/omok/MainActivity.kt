package woowacourse.omok

import android.os.Bundle
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import com.google.android.material.snackbar.Snackbar
import omok.model.position.Position
import omok.model.stone.BlackStone
import omok.model.stone.GoStone
import omok.model.stone.StoneType
import omok.model.stone.WhiteStone

class MainActivity : AppCompatActivity() {
    private var stone: GoStone = BlackStone

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val board = findViewById<TableLayout>(R.id.board)
        startOmokGame(board)
    }

    private fun startOmokGame(board: TableLayout) {
        board.children
            .filterIsInstance<TableRow>()
            .flatMap { it.children }
            .filterIsInstance<ImageView>()
            .forEachIndexed { index, view ->
                view.setOnClickListener {
                    handleStonePlacement(board, index, view)
                }
            }
    }

    private fun handleStonePlacement(
        board: TableLayout,
        index: Int,
        view: ImageView,
    ) {
        val currentStone = stone.putStone(indexAdapter(index))
        currentStone?.let {
            view.setImageResource(stone.imageView())
            if (checkOmok(board, index, view)) return
            stone = changeStone(currentStone)
        }
    }

    private fun checkOmok(
        board: TableLayout,
        index: Int,
        view: ImageView,
    ): Boolean {
        if (stone.findOmok(indexAdapter(index))) {
            val snackBar = Snackbar.make(view, "${stone.value()} 승리", Snackbar.LENGTH_INDEFINITE)
            snackBar.setAction("확인") {
            }
            snackBar.show()
            return true
        }
        return false
    }

    private fun changeStone(currentStone: StoneType): GoStone {
        return if (currentStone == StoneType.BLACK_STONE) BlackStone else WhiteStone
    }

    private fun indexAdapter(index: Int): Position {
        val row = FIRST_COLUMN + (index % BOARD_SIZE)
        val column = BOARD_SIZE - (index / BOARD_SIZE)
        return Position.of(row, column)
    }

    private fun GoStone.imageView() =
        when (this) {
            BlackStone -> R.drawable.black_stone
            WhiteStone -> R.drawable.white_stone
        }

    private fun GoStone.value() =
        when (this) {
            BlackStone -> "흑"
            WhiteStone -> "백"
        }

    companion object {
        private const val BOARD_SIZE = 15
        private const val FIRST_COLUMN = 'A'
    }
}
