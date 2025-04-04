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
                    omokBoard.put(Position(row, column), stone.color)
                    val result = game.putStone(row, column, stone.color)
                    setStoneImage(view, stone.color)
                    when (result) {
                        is GameResult.Win -> {
                            val turnColorText = if (turn.color == StoneType.BLACK) "흑" else "백"
                            showWinner(turnColorText)
                        }
                        is GameResult.Draw -> showDraw()
                        else -> turn.next()
                    }
                }

                view.setOnClickListener {
                    when (val result = game.putStone(row, column, turn.color)) {
                        is GameResult.Win -> {
                            setStoneImage(view, turn.color)
                            val turnColorText = if (turn.color == StoneType.BLACK) "흑" else "백"
                            showWinner(turnColorText)
                        }
                        is GameResult.Draw -> showDraw()
                        is GameResult.Continue -> {
                            val turnColorText = if (turn.color == StoneType.BLACK) "백" else "흑"
                            setStoneImage(view, turn.color)
                            showTurnColorToast(turnColorText)
                            turn.next()
                        }
                        is GameResult.InvalidMove -> showInvalidToast(result.message)
                    }
                }
            }
    }

    private fun setStoneImage(view: ImageView, stoneType: StoneType) {
        val image = if (stoneType == StoneType.BLACK) R.drawable.black_stone else R.drawable.white_stone
        view.setImageResource(image)
    }

    override fun onDestroy() {
        dbHelper.close()
        super.onDestroy()
    }

    private fun showTurnColorToast(turnColorText: String) {
        Toast.makeText(this, "${turnColorText}의 차례입니다.", Toast.LENGTH_SHORT).show()
    }

    private fun showInvalidToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
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
            if (turn.isWhite()) turn.next()
            showTurnColorToast("흑")
        }
        alertDialog.show()
    }

    private fun restart() {
        DatabaseStoneDAO(dbHelper).clear()
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}
