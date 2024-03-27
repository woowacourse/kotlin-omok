package woowacourse.omok

import android.os.Bundle
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import omok.model.position.Position

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val board = findViewById<TableLayout>(R.id.board)
        board
            .children
            .filterIsInstance<TableRow>()
            .flatMap { it.children }
            .filterIsInstance<ImageView>()
            .forEachIndexed { index, view -> view.setOnClickListener { view.setImageResource(R.drawable.black_stone) } }
    }

    private fun indexAdapter(index: Int): Position {
        val row = FIRST_COLUMN + (index % BOARD_SIZE)
        val column = BOARD_SIZE - (index / BOARD_SIZE)
        return Position.of(row, column)
    }

    companion object {
        private const val BOARD_SIZE = 15
        private const val FIRST_COLUMN = 'A'
    }
}
