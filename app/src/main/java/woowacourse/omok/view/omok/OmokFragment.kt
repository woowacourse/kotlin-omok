package woowacourse.omok.view.omok

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.Toast
import androidx.core.view.children
import androidx.fragment.app.Fragment
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

class OmokFragment :
    Fragment(),
    GameEventListener {
    private var gameId: Int? = null
    private val game: OmokGame = OmokGame(this)

    private lateinit var board: Board
    private lateinit var boardView: TableLayout

    private lateinit var gamesDao: GamesDao
    private lateinit var movesDao: MovesDao

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        gameId = arguments?.getInt(ARGUMENT_KEY_NAME_GAME_ID)
        return inflater.inflate(R.layout.fragment_omok, container, false)
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        initializeDb()
        initializeBoardView(view)
        loadBoardStatus()
    }

    private fun initializeDb() {
        val dbHelper = OmokDatabaseHelper(requireContext())
        gamesDao = GamesDao(dbHelper)
        movesDao = MovesDao(dbHelper)
    }

    private fun initializeBoardView(view: View) {
        boardView = view.findViewById(R.id.board)
        boardView.children.filterIsInstance<TableRow>().forEachIndexed { rowIndex, rowView ->
            rowView.children.filterIsInstance<ImageView>().forEachIndexed { colIndex, imageView ->
                val point = Point(rowIndex + 1, colIndex + 1)
                imageView.tag = point
                imageView.setOnClickListener {
                    game.placeStone(board, point)
                }
            }
        }
    }

    private fun loadBoardStatus() {
        val loadedMoves = loadMovesFromDatabase()
        board = Board(BoardSize(), loadedMoves, RuleValidator(OmokMoveRules()))
        updateBoardUIWithLoadedMoves(loadedMoves)
        val isFinished = arguments?.getBoolean(ARGUMENT_KEY_NAME_GAME_FINISHED) ?: false
        game.start(loadedMoves.entries.lastOrNull()?.toPair(), isFinished)
    }

    private fun loadMovesFromDatabase(): Map<Point, CellState> =
        gameId?.let {
            movesDao.getMoves(it).getOrDefault(emptyList()).toMap()
        } ?: emptyMap()

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
        pointView.setImageResource(getStoneImage(state))
    }

    private fun getStoneImage(state: CellState): Int =
        when (state) {
            CellState.BLACK -> R.drawable.black_stone
            CellState.WHITE -> R.drawable.white_stone
            else -> 0
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
        gameId?.let { movesDao.saveMove(it, point to state) }
    }

    override fun onGameWon(winnerState: CellState?) {
        showToast(getString(R.string.winner_ui_string, winnerState?.toUiString()))
        gameId?.let { gamesDao.updateGameStatus(it) }
    }

    override fun onShowMessage(result: PlaceStoneResult) {
        val messageResId =
            when (result) {
                is OnGoing.AlreadyPlaced -> R.string.already_placed_error_message
                is OnGoing.RuleViolation -> R.string.violation_error_message
                is OnGoing.InvalidMove -> R.string.invalid_point_error_message
                is Finished.BoardFull -> R.string.board_full_error_message
                else -> null
            }
        messageResId?.let { showToast(getString(it)) }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        const val ARGUMENT_KEY_NAME_GAME_ID = "gameId"
        const val ARGUMENT_KEY_NAME_GAME_FINISHED = "isFinished"
    }
}
