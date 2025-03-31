package woowacourse.omok

import android.content.Intent
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
import omok.domain.Turn
import woowacourse.omok.database.DatabaseStoneDAO
import woowacourse.omok.database.DbHelper
import woowacourse.omok.domain.Board
import woowacourse.omok.domain.Position
import woowacourse.omok.domain.RenjuRuleAdapter
import woowacourse.omok.domain.Stone
import woowacourse.omok.domain.StoneType

class MainActivity : AppCompatActivity() {
    private val omokBoard: Board = Board(RenjuRuleAdapter())
    private val turn = Turn()
    private val dbHelper: DbHelper = DbHelper(this)
    private val databaseStoneDAO = DatabaseStoneDAO(dbHelper)
    private val game = Game(omokBoard, turn, databaseStoneDAO)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val stones = databaseStoneDAO.queryStones()
        val board = findViewById<TableLayout>(R.id.board)
        var turnColorText = "흑"
        board
            .children
            .filterIsInstance<TableRow>()
            .flatMap { it.children }
            .filterIsInstance<ImageView>()
            .forEachIndexed { index, view ->
                val row = index / Board.BOARD_SIZE
                val column = index % Board.BOARD_SIZE

                val stone = stones.find { it.position.row == row && it.position.column == column }
                if (stone != null) {
                    if (stone.color == StoneType.WHITE) {
                        view.setImageResource(R.drawable.white_stone)
                    } else {
                        view.setImageResource(R.drawable.black_stone)
                    }
                }

                view.setOnClickListener {
                    if (omokBoard.isInvalidPosition(Position(row, column))) {
                        Toast.makeText(this, "이미 돌을 놓은 자리입니다.", Toast.LENGTH_SHORT).show()
                        return@setOnClickListener
                    }
                    if (omokBoard.isInvalidBlackPosition(Stone(Position(row, column), turn.color))) {
                        Toast.makeText(this, "흑돌이 놓을 수 없는 금수입니다.", Toast.LENGTH_SHORT).show()
                        return@setOnClickListener
                    }

                    game.putStone(view, row, column, turn.color)

                    if (omokBoard.isFull()) {
                        showDraw()
                    } else if (game.checkOmok()) {
                        showWinner(turnColorText)
                    } else {
                        turn.next()
                        turnColorText = if (turn.isWhite()) "백" else "흑"
                        showTurnColorToast(turnColorText)
                    }
                }
            }
        showTurnColorToast(turnColorText)
    }

    override fun onDestroy() {
        dbHelper.close()
        super.onDestroy()
    }

    private fun showTurnColorToast(turnColorText: String) {
        Toast.makeText(this, "${turnColorText}의 차례입니다.", Toast.LENGTH_SHORT).show()
    }

    private fun showDraw() {
        val alertDialog =
            AlertDialog.Builder(this).run {
                setMessage("더 이상 돌을 놓을 수 없어 무승부입니다.")
            }

        alertDialog.setOnDismissListener {
            restart()
        }
        alertDialog.show()
    }

    private fun showWinner(turnColorText: String) {
        val alertDialog =
            AlertDialog.Builder(this).run {
                setMessage("${turnColorText}의 승리입니다!")
            }

        alertDialog.setOnDismissListener {
            restart()
        }
        alertDialog.show()
    }

    private fun restart() {
        DatabaseStoneDAO(dbHelper).clear()
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}
