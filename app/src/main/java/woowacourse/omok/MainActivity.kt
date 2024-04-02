package woowacourse.omok

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import woowacourse.omok.database.DatabaseHelper
import woowacourse.omok.database.GameDao
import woowacourse.omok.database.GameDaoImpl
import woowacourse.omok.model.board.CoordsNumber
import woowacourse.omok.model.board.Position
import woowacourse.omok.model.board.Stone
import woowacourse.omok.model.omokGame.OmokGame
import woowacourse.omok.view.OutputView

class MainActivity : AppCompatActivity() {
    private lateinit var gameDao: GameDao
    val omok = OmokGame(OutputView())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val board = findViewById<TableLayout>(R.id.board)
        gameDao = GameDaoImpl(DatabaseHelper(this))
        omok.loadGame(gameDao)
        updateUI()
        generateResetBoardButton()

        board.children.filterIsInstance<TableRow>().forEachIndexed { rowIndex, rows ->
            rows.children.filterIsInstance<ImageView>().forEachIndexed { columIndex, view ->
                view.setOnClickListener {
                    processPlayerMove(rowIndex, columIndex)
                }
            }
        }
    }

    private fun generateResetBoardButton() {
        val button = findViewById<Button>(R.id.resetButton)
        button.setOnClickListener {
            resetBoard()
        }
    }


    private fun processPlayerMove(rowIndex: Int, columnIndex: Int) {
        if (omok.processPlayerMove(rowIndex, columnIndex)) {
            updateUI()
            if (omok.isGameOver()) {
                Toast.makeText(this, "Winner: ${omok.currentStone}", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Invalid move.", Toast.LENGTH_SHORT).show()
        }
        omok.saveGame(gameDao)
    }


    private fun resetBoard() {
        omok.resetGame()
        gameDao.resetGame()
        updateUI()
    }

    private fun updateUI() {
        val board = findViewById<TableLayout>(R.id.board)
        val forbiddenPositions = omok.findForbiddenPositions()
        board.children.filterIsInstance<TableRow>().forEachIndexed { rowIndex, rows ->
            rows.children.filterIsInstance<ImageView>().forEachIndexed { columnIndex, view ->
                updateCellView(columnIndex, rowIndex, forbiddenPositions, view)
            }
        }
    }

    private fun updateCellView(
        columnIndex: Int,
        rowIndex: Int,
        forbiddenPositions: List<Position>,
        view: ImageView,
    ) {
        if (Position(CoordsNumber(columnIndex), CoordsNumber(rowIndex)) in forbiddenPositions) {
            view.setImageResource(R.drawable.x_mark)
        } else {
            val stone = omok.getStoneAt(rowIndex, columnIndex)
            val resId = when (stone) {
                Stone.BLACK -> R.drawable.black_stone
                Stone.WHITE -> R.drawable.white_stone
                else -> 0
            }
            if (resId != 0) view.setImageResource(resId)
            else view.setImageDrawable(null)
        }
    }

}
