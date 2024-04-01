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
import woowacourse.omok.db.GameDatabaseHelper
import woowacourse.omok.model.OmokGame

class MainActivity : AppCompatActivity() {
    private lateinit var omokGame: OmokGame
    private lateinit var boardViews: Array<Array<ImageView?>>
    private lateinit var dbHelper: GameDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initializeGameFromDatabase()
        setupButtons()
        initializeBoard()
    }

    private fun initializeGameFromDatabase() {
        dbHelper = GameDatabaseHelper(this)
        omokGame = OmokGame(RenjuRule)
        val savedState = dbHelper.loadGameState()
        if (savedState.isNotEmpty()) {
            val isRestoreSuccessful = omokGame.restoreFrom(savedState)
            if (!isRestoreSuccessful) {
                Toast.makeText(this, "게임 상태 복원에 실패했습니다. 새 게임을 시작합니다.", Toast.LENGTH_LONG).show()
                dbHelper.clearGameState()
            }
        }
    }

    private fun setupButtons() {
        findViewById<Button>(R.id.btnClear).setOnClickListener {
            clearGameBoard()
            dbHelper.clearGameState()
        }
        findViewById<Button>(R.id.btnSaveAndExit).setOnClickListener {
            val currentState = omokGame.saveTo()
            if (dbHelper.saveGameState(currentState)) finish()
        }
    }

    private fun initializeBoard() {
        val board = findViewById<TableLayout>(R.id.board)
        boardViews = Array(OmokGame.BOARD_SIZE) { arrayOfNulls(OmokGame.BOARD_SIZE) }
        board.children.filterIsInstance<TableRow>().forEachIndexed { row, rowView ->
            rowView.children.filterIsInstance<ImageView>()
                .forEachIndexed { col, imageView ->
                    boardViews[row][col] = imageView
                    imageView.setOnClickListener { handleStonePlacement(row, col) }
                    updateBoardViews(row, col, imageView)
                }
        }
    }

    private fun handleStonePlacement(row: Int, col: Int) {
        if (omokGame.placeStoneOnBoard(
                omokGame.generateNextOmokStone(
                    BoardPosition(BoardCoordinate(row), BoardCoordinate(col))
                )
            )
        ) {
            updateBoardViews(row, col, boardViews[row][col]!!)
            checkGameOver()
        } else {
            Toast.makeText(this, "놓을 수 없는 자리 입니다.", Toast.LENGTH_LONG).show()
        }
    }

    private fun updateBoardViews(row: Int, col: Int, imageView: ImageView) {
        val stoneType = omokGame.getStoneTypeAtPosition(row, col)
        when (stoneType) {
            OmokStoneType.WHITE -> imageView.setImageResource(R.drawable.white_stone)
            OmokStoneType.BLACK -> imageView.setImageResource(R.drawable.black_stone)
            else -> imageView.setImageResource(0)
        }
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
        omokGame.resetGame()
        for (row in boardViews.indices) {
            for (col in boardViews[row].indices) {
                boardViews[row][col]?.setImageResource(0)
            }
        }
    }
}
