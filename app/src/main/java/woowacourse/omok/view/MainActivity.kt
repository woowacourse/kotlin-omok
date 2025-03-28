package woowacourse.omok.view

import android.app.AlertDialog
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
import woowacourse.omok.R
import woowacourse.omok.data.OmokDatabaseHelper
import woowacourse.omok.data.dao.GamesDao
import woowacourse.omok.data.dao.MovesDao
import woowacourse.omok.domain.OmokGame
import woowacourse.omok.domain.board.Board
import woowacourse.omok.domain.board.BoardSize
import woowacourse.omok.domain.board.CellState
import woowacourse.omok.domain.board.Point
import woowacourse.omok.domain.board.result.Finished
import woowacourse.omok.domain.board.result.OnGoing
import woowacourse.omok.domain.board.result.PlaceStoneResult
import woowacourse.omok.domain.rule.OmokMoveRules
import woowacourse.omok.domain.rule.RuleValidator

class MainActivity : AppCompatActivity() {
    private lateinit var board: Board
    private lateinit var boardView: TableLayout
    private lateinit var game: OmokGame

    private lateinit var gamesDao: GamesDao
    private lateinit var movesDao: MovesDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setWindowInsets()

        initializeDb()
        initializeBoardView()
        initializeGame()
    }

    private fun initializeDb() {
        val dbHelper = OmokDatabaseHelper(this)

        gamesDao = GamesDao(dbHelper)
        movesDao = MovesDao(dbHelper)

        // 추가 기능 미구현
        val ids = gamesDao.getGameIds()
        if (ids.isEmpty()) gamesDao.addGame(GAME_ROOM_ID)
    }

    private fun initializeBoardView() {
        boardView = findViewById(R.id.board)
        boardView.children.filterIsInstance<TableRow>().forEachIndexed { rowIndex, rowView ->
            rowView.children.filterIsInstance<ImageView>().forEachIndexed { colIndex, view ->
                val point = Point(rowIndex + 1, colIndex + 1)
                view.tag = point
                view.setOnClickListener {
                    game.placeStone(board, point)
                }
            }
        }
    }

    private fun initializeGame() {
        game = OmokGame(GameListener())
        loadBoardStatus()
    }

    private fun loadBoardStatus() {
        val loadedMoves = movesDao.getMoves(GAME_ROOM_ID).toMap()
        board = Board(BoardSize(), loadedMoves, RuleValidator(OmokMoveRules()))
        updateBoardUIWithLoadedMoves(loadedMoves)
        game.start(loadedMoves.entries.lastOrNull()?.toPair())
    }

    private fun updateBoardUIWithLoadedMoves(loadedMoves: Map<Point, CellState>) {
        loadedMoves.forEach { (point, stoneColor) ->
            updateBoardUI(point, stoneColor)
        }
    }

    private fun updateBoardUI(
        point: Point,
        state: CellState,
    ) {
        val pointView = boardView.findViewWithTag<ImageView>(point)
        val stoneImage = getStoneImage(state)
        pointView.setImageResource(stoneImage)
    }

    private fun getStoneImage(state: CellState): Int =
        when (state) {
            CellState.BLACK -> R.drawable.black_stone
            CellState.WHITE -> R.drawable.white_stone
            else -> 0
        }

    private fun resetGame() {
        clearBoardImages()
        gamesDao.deleteGame(GAME_ROOM_ID)
        loadBoardStatus()
    }

    private fun clearBoardImages() {
        boardView.children.filterIsInstance<TableRow>().forEach { rowView ->
            rowView.children.filterIsInstance<ImageView>().forEach { view ->
                view.setImageResource(0)
            }
        }
    }

    private inner class GameListener : OmokGameListener {
        override fun onBoardUpdated(
            point: Point,
            state: CellState,
        ) {
            updateBoardUI(point, state)
            movesDao.saveMove(GAME_ROOM_ID, point to state)
        }

        override fun onGameWon(winnerState: CellState?) {
            showGameOverDialog(winnerState)
        }

        override fun onShowMessage(result: PlaceStoneResult) {
            when (result) {
                is OnGoing.AlreadyPlaced -> getString(R.string.already_placed_error_message)
                is OnGoing.RuleViolation -> getString(R.string.violation_error_message)
                is OnGoing.InvalidMove -> getString(R.string.invalid_point_error_message)
                is Finished.BoardFull -> getString(R.string.board_full_error_message)
                else -> null
            }?.also { message ->
                Toast.makeText(this@MainActivity, message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showGameOverDialog(winnerState: CellState?) {
        AlertDialog
            .Builder(this)
            .setTitle(getString(R.string.dialog_title_game_over))
            .setMessage(getString(R.string.dialog_description_winner, winnerState?.toUiString()))
            .setPositiveButton(getString(R.string.dialog_button_positive)) { dialog, _ ->
                dialog.dismiss()
                resetGame()
            }.setCancelable(false)
            .show()
    }

    private fun setWindowInsets() {
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun CellState.toUiString(): String =
        when (this) {
            CellState.BLACK -> getString(R.string.black_ui_string)
            CellState.WHITE -> getString(R.string.white_ui_string)
            else -> ""
        }

    companion object {
        private const val GAME_ROOM_ID = 1
    }
}
