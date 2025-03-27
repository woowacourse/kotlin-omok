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
import woowacourse.omok.domain.OmokGame
import woowacourse.omok.domain.board.Board
import woowacourse.omok.domain.board.BoardSize
import woowacourse.omok.domain.board.Point
import woowacourse.omok.domain.board.StoneColor
import woowacourse.omok.domain.rule.RuleValidator

class MainActivity : AppCompatActivity() {
    private lateinit var board: Board
    private lateinit var boardView: TableLayout
    private lateinit var game: OmokGame
    private lateinit var dbHelper: OmokDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setWindowInsets()

        initializeDbHelper()
        initializeBoardView()
        initializeGame()
    }

    private fun initializeDbHelper() {
        dbHelper = OmokDatabaseHelper(this)
        val ids = dbHelper.getGameIds()
        println(ids)
        if (ids.isEmpty()) dbHelper.addGame(GAME_ROOM_ID)
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
        val loadedMoves = dbHelper.getMoves(GAME_ROOM_ID).toMap()
        board = Board(BoardSize(), loadedMoves, RuleValidator())
        updateBoardUIWithLoadedMoves(loadedMoves)
        game.start(loadedMoves.entries.lastOrNull()?.toPair())
    }

    private fun updateBoardUIWithLoadedMoves(loadedMoves: Map<Point, StoneColor>) {
        loadedMoves.forEach { (point, stoneColor) ->
            updateBoardUI(point, stoneColor)
        }
    }

    private fun updateBoardUI(
        point: Point,
        color: StoneColor,
    ) {
        val pointView = boardView.findViewWithTag<ImageView>(point)
        val stoneImage = getStoneImage(color)
        pointView.setImageResource(stoneImage)
    }

    private fun getStoneImage(color: StoneColor): Int =
        when (color) {
            StoneColor.BLACK -> R.drawable.black_stone
            StoneColor.WHITE -> R.drawable.white_stone
            else -> 0
        }

    private fun resetGame() {
        clearBoardImages()
        dbHelper.deleteGame(GAME_ROOM_ID)
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
            color: StoneColor,
        ) {
            updateBoardUI(point, color)
            dbHelper.saveMove(GAME_ROOM_ID, point to color)
        }

        override fun onGameWon(winnerState: StoneColor?) {
            showGameOverDialog(winnerState)
        }

        override fun onError(message: String) {
            showToast(message)
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun showGameOverDialog(winnerState: StoneColor?) {
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

    private fun StoneColor.toUiString(): String =
        when (this) {
            StoneColor.BLACK -> getString(R.string.black_ui_string)
            StoneColor.WHITE -> getString(R.string.white_ui_string)
            else -> ""
        }

    override fun onDestroy() {
        super.onDestroy()

        dbHelper.close()
    }

    companion object {
        private const val GAME_ROOM_ID = 1
    }
}
