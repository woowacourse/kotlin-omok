package woowacourse.omok

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import omok.library.RenjuRule
import omok.model.BoardCoordinate
import omok.model.BoardPosition
import omok.model.OmokStoneType
import omok.view.OutputView
import woowacourse.omok.db.GameDatabaseHelper
import woowacourse.omok.model.OmokGame

class MainActivity : AppCompatActivity() {
    private lateinit var omokGame: OmokGame
    private lateinit var boardViews: Array<Array<ImageView?>>
    private lateinit var dbHelper: GameDatabaseHelper
    var positions = listOf<BoardPosition>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setButtons()
        getDatabase()
        initializeBoard()
        OutputView.printOmokGameBoard(omokGame.getBoard(), omokGame.getForbiddenPositions())

    }

    private fun getDatabase() {
        dbHelper = GameDatabaseHelper(this)
        omokGame = OmokGame(RenjuRule)

        val savedState = dbHelper.loadGameState()
        if (savedState.isNotEmpty()) {
            omokGame.restoreFrom(savedState)
            positions = omokGame.getForbiddenPositions()
        }
    }

    private fun setButtons() {
        findViewById<Button>(R.id.btnClear).setOnClickListener {
            clearGameBoard()
            dbHelper.clearGameState()
        }
        findViewById<Button>(R.id.btnSaveAndExit).setOnClickListener {
            val currentState = omokGame.saveTo()
            dbHelper.saveGameState(currentState)
            finish()
        }
    }

    private fun initializeBoard() {
        val board = findViewById<TableLayout>(R.id.board)
        boardViews = Array(15) { arrayOfNulls<ImageView>(15) }

        board.children.filterIsInstance<TableRow>().forEachIndexed { row, viewGroup ->
            viewGroup.children.filterIsInstance<ImageView>()
                .forEachIndexed { col, imageView ->
                    boardViews[row][col] = imageView
                    updateSingleCell(imageView, row, col)
                    imageView.setOnClickListener {
                        handleStonePlacement(row, col, it as ImageView)
                    }
                }
        }
    }


    private fun handleStonePlacement(row: Int, col: Int, imageView: ImageView) {
        val position = BoardPosition(BoardCoordinate(row), BoardCoordinate(col))
        if (omokGame.placeStoneOnBoard(omokGame.generateNextOmokStone(position))) {
            updateForbiddenPositions()
            updateSingleCell(imageView, row, col)
            checkGameOver()
            OutputView.printOmokGameBoard(omokGame.getBoard(), omokGame.getForbiddenPositions())
        } else {
            OutputView.printInvalidPositionMessage()
        }
    }


    private fun updateForbiddenPositions() {
        removePreviousForbiddenPositions()
        addNewForbiddenPositions()
    }

    private fun removePreviousForbiddenPositions() {
        positions.filter { it !in omokGame.getForbiddenPositions() }.forEach { forbiddenPosition ->
            val row = forbiddenPosition.getRow()
            val col = forbiddenPosition.getColumn()
            val imageView = boardViews[row][col]
            imageView?.apply {
                setImageResource(0)
            }
        }
    }

    private fun addNewForbiddenPositions() {
        positions = omokGame.getForbiddenPositions()
        positions.forEach { forbiddenPosition ->
            val row = forbiddenPosition.getRow()
            val col = forbiddenPosition.getColumn()
            val imageView = boardViews[row][col]

            imageView?.apply {
                setImageResource(R.drawable.x)
            }
        }
    }


    private fun updateSingleCell(imageView: ImageView, row: Int, col: Int) {
        imageView.setImageResource(
            when {
                omokGame.getBoard()[row][col].getOmokStoneType() == OmokStoneType.WHITE -> R.drawable.white_stone
                omokGame.getBoard()[row][col].getOmokStoneType() == OmokStoneType.BLACK -> R.drawable.black_stone
                isInForbiddenPositions(row, col) -> R.drawable.x
                else -> 0
            }
        )
    }

    private fun checkGameOver() {
        if (!omokGame.isRunning()) {
            Toast.makeText(this, "게임이 종료되었습니다. 오목판이 초기화됩니다.", Toast.LENGTH_SHORT).show()
            Handler(Looper.getMainLooper()).postDelayed({
                clearGameBoard()
                dbHelper.clearGameState()
            }, 1000)
        }
    }


    private fun clearGameBoard() {
        for (row in boardViews.indices) {
            for (col in boardViews[row].indices) {
                boardViews[row][col]?.setImageResource(0)
            }
        }
        omokGame = OmokGame(RenjuRule)
        updateForbiddenPositions()
    }

    private fun isInForbiddenPositions(row: Int, col: Int): Boolean {
        if (BoardPosition(
                BoardCoordinate(row),
                BoardCoordinate(col)
            ) in positions
        ) {
            return true
        }
        return false
    }
}

