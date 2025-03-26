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
import woowacourse.omok.model.OmokGame
import woowacourse.omok.model.board.Board
import woowacourse.omok.model.board.BoardSize
import woowacourse.omok.model.board.Point
import woowacourse.omok.model.board.StoneColor
import woowacourse.omok.model.rule.RuleValidator

class MainActivity : AppCompatActivity() {
    private lateinit var board: Board
    private lateinit var game: OmokGame

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setWindowInsets()

        initializeGame()
        initializeBoard()
    }

    private fun initializeGame() {
        val boardView = findViewById<TableLayout>(R.id.board)
        initializeBoardView(boardView)
        game = OmokGame(GameListener(boardView))
    }

    private fun initializeBoard() {
        board = Board(BoardSize(), RuleValidator())
        game.start()
    }

    private fun initializeBoardView(boardView: TableLayout) {
        boardView.children.filterIsInstance<TableRow>().forEachIndexed { rowIndex, rowView ->
            rowView.children.filterIsInstance<ImageView>().forEachIndexed { colIndex, view ->
                view.tag = Point(rowIndex + 1, colIndex + 1)
                view.setOnClickListener {
                    game.placeStone(board, view.tag as Point)
                }
            }
        }
    }

    private fun resetGame() {
        val boardView = findViewById<TableLayout>(R.id.board)
        boardView.children.filterIsInstance<TableRow>().forEach { rowView ->
            rowView.children.filterIsInstance<ImageView>().forEach { view ->
                view.setImageResource(0)
            }
        }

        initializeBoard()
    }

    private inner class GameListener(
        private val boardView: TableLayout,
    ) : OmokGameListener {
        override fun onStartGame() {
            initializeGame()
        }

        override fun onBoardUpdated(
            point: Point,
            color: StoneColor,
        ) {
            updateBoardUI(boardView, point, color)
        }

        override fun onGameWon(winnerState: StoneColor?) {
            showGameOverDialog(winnerState)
        }

        override fun onError(message: String) {
            showToast(message)
        }
    }

    private fun updateBoardUI(
        boardView: TableLayout,
        point: Point,
        color: StoneColor,
    ) {
        val stoneImage =
            when (color) {
                StoneColor.BLACK -> R.drawable.black_stone
                StoneColor.WHITE -> R.drawable.white_stone
                StoneColor.NONE -> null
            }

        val pointView = boardView.findViewWithTag<ImageView>(point)
        pointView.setImageResource(stoneImage ?: 0)
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun showGameOverDialog(winnerState: StoneColor?) {
        AlertDialog
            .Builder(this)
            .setTitle("게임 종료")
            .setMessage("${winnerState?.toUiString()}돌 승리!")
            .setPositiveButton("확인") { dialog, _ ->
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
            StoneColor.BLACK -> "흑"
            StoneColor.WHITE -> "백"
            StoneColor.NONE -> ""
        }
}
