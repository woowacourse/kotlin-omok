package woowacourse.omok.view

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

class MainActivity :
    AppCompatActivity(),
    GameEventListener {
    private val game: OmokGame = OmokGame(this)
    private lateinit var board: Board
    private lateinit var boardView: TableLayout

    private lateinit var gamesDao: GamesDao
    private lateinit var movesDao: MovesDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setWindowInsets()

        initializeDb()
        initializeBoardView()
        loadBoardStatus()
    }

    private fun initializeDb() {
        val dbHelper = OmokDatabaseHelper(this)

        gamesDao = GamesDao(dbHelper)
        movesDao = MovesDao(dbHelper)

        // 추가 기능 미구현
        val ids = gamesDao.getGameIds()
        if (ids.isEmpty()) gamesDao.addGame(1)
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

    private fun loadBoardStatus() {
        val loadedMoves = movesDao.getMoves(1).toMap()
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
        gamesDao.deleteGame(1)
        loadBoardStatus()
    }

    private fun clearBoardImages() {
        boardView.children.filterIsInstance<TableRow>().forEach { rowView ->
            rowView.children.filterIsInstance<ImageView>().forEach { view ->
                view.setImageResource(0)
            }
        }
    }

    private fun showGameOverDialog(winnerState: CellState?) {
        winnerState?.toUiString()?.let { GameOverDialog(this).show(it) { resetGame() } }
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

    override fun onBoardUpdated(
        point: Point,
        state: CellState,
    ) {
        updateBoardUI(point, state)
        movesDao.saveMove(1, point to state)
    }

    override fun onGameWon(winnerState: CellState?) {
        showGameOverDialog(winnerState)
    }

    override fun onShowMessage(result: PlaceStoneResult) {
        val messageRes =
            when (result) {
                is OnGoing.AlreadyPlaced -> R.string.already_placed_error_message
                is OnGoing.RuleViolation -> R.string.violation_error_message
                is OnGoing.InvalidMove -> R.string.invalid_point_error_message
                is Finished.BoardFull -> R.string.board_full_error_message
                else -> null
            }

        messageRes?.let {
            Toast.makeText(this, getString(it), Toast.LENGTH_SHORT).show()
        }
    }
}
