package woowacourse.omok

import android.os.Bundle
import android.widget.TableLayout
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import woowacourse.omok.database.DbHelper
import woowacourse.omok.database.OmokDao
import woowacourse.omok.model.Board
import woowacourse.omok.model.game.GameState
import woowacourse.omok.model.game.ViolationResult
import woowacourse.omok.model.stone.Point
import woowacourse.omok.model.stone.Stones
import woowacourse.omok.ui.OmokMainView

class MainActivity : AppCompatActivity() {
    private val omokDao: OmokDao = OmokDao(DbHelper(this))

    private lateinit var omokMainView: OmokMainView
    private lateinit var board: Board

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initBoard()
        initOmokMainView()
        omokMainView.setTurnTextView(board)
        omokMainView.paintEntirePoints(board.stones)
    }

    override fun onDestroy() {
        omokDao.close()
        super.onDestroy()
    }

    private fun initBoard() {
        val stones: Stones = omokDao.readStones()
        board = Board(stones)
    }

    private fun initOmokMainView() {
        val boardTableLayout = findViewById<TableLayout>(R.id.board)
        omokMainView =
            OmokMainView(
                this,
                boardTableLayout,
                onBoardPointClick = ::placeWithCheck,
                onResetButtonClick = ::reset,
            )
    }

    internal fun reset() {
        omokDao.deleteStones()
        initBoard()
        omokMainView.clearBoardImageViews()
        omokMainView.paintEntirePoints(board.stones)
        omokMainView.setTurnTextView(board)
        omokMainView.setBoardClickability(true)
    }

    private fun place(point: Point) {
        val stone = board.currentStone(point)
        board.place(stone)
        omokMainView.paintStone(stone)
        omokDao.insertStone(stone)
    }

    private fun placeWithCheck(point: Point) {
        val stone = board.currentStone(point)
        when (val violationResult = board.checkViolation(stone)) {
            is ViolationResult.Success -> place(point)
            is ViolationResult.Failure.InvalidMoveResult.FullBoard -> {
                showToastMessage(violationResult.message)
                omokMainView.setBoardClickability(false)
                omokDao.deleteStones()
            }

            is ViolationResult.Failure -> showToastMessage(violationResult.message)
        }

        if (board.gameState(stone) != GameState.PLAYING) {
            showToastMessage(board.gameState(stone).toWinnerMessage())
            omokMainView.setBoardClickability(false)
            omokDao.deleteStones()
        }
        omokMainView.setTurnTextView(board)
    }

    private fun showToastMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun GameState.toWinnerMessage(): String =
        when (this) {
            GameState.WHITE_OMOK -> getString(R.string.message_white_win)
            GameState.BLACK_OMOK -> getString(R.string.message_black_win)
            GameState.PLAYING -> ""
        }
}
