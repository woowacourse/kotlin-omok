package woowacourse.omok

import android.content.DialogInterface
import android.os.Bundle
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.children
import omok.domain.Board
import omok.domain.FiveRule
import omok.domain.Position
import omok.domain.RenjuRuleAdapter
import omok.domain.Turn

class MainActivity : AppCompatActivity() {
    private val omokBoard: Board = Board(RenjuRuleAdapter())
    private val turn = Turn()
    private val fiveRule = FiveRule()

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
                    showTurnColorToast(checkOmok())
                }
            }
        showTurnColorToast(checkOmok())
    }

    override fun onRestart() {
        super.onRestart()
        omokBoard.clear()
        turn.reset()

        val board = findViewById<TableLayout>(R.id.board)
        board
            .children
            .filterIsInstance<TableRow>()
            .flatMap { it.children }
            .filterIsInstance<ImageView>()
            .forEach { view -> view.setImageDrawable(null)
            }
        showTurnColorToast(checkOmok())
    }

    private fun putStone(
        view: ImageView,
        row: Int,
        column: Int,
    ) {
        omokBoard.put(Position(row, column), turn.color)
        if (turn.isWhite()) {
            view.setImageResource(R.drawable.white_stone)
        } else {
            view.setImageResource(R.drawable.black_stone)
        }
        turn.next()
    }

    private fun showTurnColorToast(isOmok: Boolean) {
        val turnColor: String
        if (isOmok) {
            turnColor = if (turn.isWhite()) "흑" else "백"
            Toast.makeText(this, "${turnColor}의 승리입니다!", Toast.LENGTH_SHORT).show()
            showWinner(turnColor)
        } else {
            turnColor = if (turn.isWhite()) "백" else "흑"
            Toast.makeText(this, "${turnColor}의 차례입니다.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showWinner(turnColor: String) {
        val restartEventHandle = object : DialogInterface.OnClickListener {
            override fun onClick(p0: DialogInterface?, p1: Int) {
                if (p1 == DialogInterface.BUTTON_NEGATIVE) {
                    onRestart()
                }
            }
        }
        AlertDialog.Builder(this).run {
            setMessage("${turnColor}의 승리입니다!")
            setNegativeButton("다시 시작", restartEventHandle)
        }.show()
    }

    private fun checkOmok(): Boolean {
        val lastStone = omokBoard.stones.lastStone()
        if (lastStone != null) {
            return fiveRule.isOmok(lastStone, omokBoard.stones)
        }
        return false
    }
}
