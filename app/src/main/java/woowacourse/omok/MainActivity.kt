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
import omok.domain.Board
import omok.domain.Position
import omok.domain.RenjuRuleAdapter
import omok.domain.Turn

class MainActivity : AppCompatActivity() {
    private val omokBoard: Board = Board(RenjuRuleAdapter())
    private val turn = Turn()
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
            .flatMap { it.children }
            .filterIsInstance<ImageView>()
            .forEachIndexed { index, view ->
                val row = index / Board.BOARD_SIZE
                val column = index % Board.BOARD_SIZE
                view.setOnClickListener {
                    putStone(view, row, column)
                    showTurnColorToast()
                } }
        showTurnColorToast()
    }

    private fun putStone(view: ImageView, row: Int, column: Int) {
        omokBoard.put(Position(row, column), turn.color)
        if (turn.isWhite()) {
            view.setImageResource(R.drawable.white_stone)
        } else {
            view.setImageResource(R.drawable.black_stone)
        }
        turn.next()
    }

    private fun showTurnColorToast() {
        val nextTurnColor: String = if (turn.isWhite()) "백" else "흑"
        val toastTurn = Toast.makeText(this, "${nextTurnColor}의 차례입니다.", Toast.LENGTH_SHORT)
        toastTurn.show()
    }
}
