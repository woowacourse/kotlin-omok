package woowacourse.omok

import android.os.Bundle
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.children
import woowacourse.omok.db.BoardDao
import woowacourse.omok.db.BoardDaoImpl
import woowacourse.omok.db.BoardDto
import woowacourse.omok.db.TurnDao
import woowacourse.omok.db.TurnDaoImpl
import woowacourse.omok.model.board.OmokBoard
import woowacourse.omok.model.board.Position
import woowacourse.omok.model.board.PositionState
import woowacourse.omok.model.player.Turn
import woowacourse.omok.model.stone.StoneColor

class MainActivity : AppCompatActivity() {
    private lateinit var boardDao: BoardDao
    private lateinit var turn: Turn
    private lateinit var turnDao: TurnDao
    private val omokBoard = OmokBoard()
    private val displayGame: Unit by lazy { displayGame(reset = false) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        loadGame()
        restoreBoard()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        displayGame
    }

    private fun loadGame() {
        turnDao = TurnDaoImpl(this)
        turn = Turn(turnDao)
        boardDao = BoardDaoImpl(this)
    }

    private fun displayGame(reset: Boolean) {
        val board = findViewById<TableLayout>(R.id.board)

        board.children
            .filterIsInstance<TableRow>()
            .forEach { row ->
                row.children
                    .filterIsInstance<ImageView>()
                    .forEach { view ->
                        if (reset) view.setImageResource(0)
                        view.setOnClickListener {
                            val y = board.indexOfChild(row) + 1
                            val x = row.indexOfChild(view) + 1
                            handleStoneClick(view, Position(x, y))
                        }
                    }
            }
    }

    private fun restoreBoard() {
        val stones = boardDao.getAllStones()
        val board = findViewById<TableLayout>(R.id.board)

        for (stone in stones) {
            val x = stone.x
            val y = stone.y
            val color = StoneColor.valueOf(stone.stoneColor)
            val position = Position(x, y)

            omokBoard.board[position] =
                when (color) {
                    StoneColor.BLACK -> PositionState.BLACK_POSITION
                    StoneColor.WHITE -> PositionState.WHITE_POSITION
                }

            val row = board.getChildAt(y - 1) as TableRow
            val view = row.getChildAt(x - 1) as ImageView
            view.setImageResource(
                when (omokBoard.board[position]) {
                    PositionState.BLACK_POSITION -> R.drawable.black_stone
                    PositionState.WHITE_POSITION -> R.drawable.white_stone
                    else -> continue
                },
            )
        }
    }

    private fun handleStoneClick(
        view: ImageView,
        position: Position,
    ) {
        val placed = turn.place(position, omokBoard)
        if (!placed) {
            return
        }

        if (turn.forbidden()) {
            showDialog(FORBIDDEN_MESSAGE, reset = false)
            return
        }

        boardDao.insertStone(
            BoardDto(
                position.xPoint,
                position.yPoint,
                turn.currentStoneColor.name,
            ),
        )
        showStones(view)

        if (turn.win()) {
            showWinDialog(turn.currentStoneColor)
        }

        turn.next()
    }

    private fun showStones(view: ImageView) {
        view.setImageResource(
            when (turn.currentStoneColor) {
                StoneColor.BLACK -> R.drawable.black_stone
                StoneColor.WHITE -> R.drawable.white_stone
            },
        )
    }

    private fun showWinDialog(winner: StoneColor) {
        val winnerText =
            when (winner) {
                StoneColor.BLACK -> BLACK_STONE_WIN
                StoneColor.WHITE -> WHITE_STONE_WIN
            }

        showDialog(winnerText, reset = true)
    }

    private fun showDialog(
        text: String?,
        reset: Boolean,
    ) {
        AlertDialog.Builder(this)
            .setMessage(text)
            .setPositiveButton(CONFIRM) { _, _ ->
                if (reset) resetGame()
            }
            .setCancelable(false)
            .show()
    }

    private fun resetGame() {
        turnDao.deleteTurn()
        boardDao.clearBoard()
        loadGame()
        displayGame(reset = true)
    }

    companion object {
        private const val FORBIDDEN_MESSAGE = "금수입니다."
        private const val BLACK_STONE_WIN = "흑돌 승리"
        private const val WHITE_STONE_WIN = "백돌 승리"
        private const val CONFIRM = "확인"
    }
}
