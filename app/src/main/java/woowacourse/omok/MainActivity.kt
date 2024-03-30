package woowacourse.omok

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import woowacourse.omok.db.OmokDao
import woowacourse.omok.domain.model.BlackTurn
import woowacourse.omok.domain.model.Board
import woowacourse.omok.domain.model.FinishedTurn
import woowacourse.omok.domain.model.Point
import woowacourse.omok.domain.model.RuleAdapter
import woowacourse.omok.domain.model.Stone
import woowacourse.omok.domain.model.StoneType
import woowacourse.omok.domain.model.Turn
import woowacourse.omok.domain.view.OutputView.MESSAGE_GAME_END
import woowacourse.omok.domain.view.OutputView.MESSAGE_GAME_START
import woowacourse.omok.domain.view.OutputView.MESSAGE_INVALID_POINT_INPUT
import woowacourse.omok.domain.view.OutputView.MESSAGE_TURN
import woowacourse.omok.domain.view.OutputView.MESSAGE_WINNER
import woowacourse.omok.domain.view.OutputView.STONE_TYPE_BLACK
import woowacourse.omok.domain.view.OutputView.generateTurnMessage
import woowacourse.omok.domain.view.OutputView.generateWinnerMessage

class MainActivity : AppCompatActivity() {
    private lateinit var board: Board
    private lateinit var ruleAdapter: RuleAdapter
    private lateinit var turn: Turn
    private var onGame: Boolean = true
    private var toast: Toast? = null
    private val omokDao: OmokDao by lazy { OmokDao(this) }
    private val tableLayoutBoard: List<ImageView> by lazy {
        findViewById<TableLayout>(R.id.board)
            .children
            .filterIsInstance<TableRow>()
            .flatMap { tableRow ->
                tableRow.children.filterIsInstance<ImageView>().toList()
            }
            .toList()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setUpTableLayoutBoard()
        setUpRestartButton()

        gameStart()
    }

    private fun setUpTableLayoutBoard() {
        tableLayoutBoard.forEachIndexed { index, view ->
            val x = index / BOARD_SIZE
            val y = index % BOARD_SIZE

            view.setOnClickListener {
                if (!onGame) {
                    displayMessage(MESSAGE_GAME_END)
                    return@setOnClickListener
                }
                progressGameTurn(Point(y, 14 - x), view)
                onGame = judgeGameState()
            }
        }
    }

    private fun setUpRestartButton() {
        val reStartButton: Button = findViewById(R.id.reStartButton)
        reStartButton.setOnClickListener {
            gameRestart()
        }
    }

    private fun gameStart() {
        board = Board(BOARD_SIZE)
        ruleAdapter = RuleAdapter(board)
        turn = BlackTurn()
        onGame = true
        displayMessage(MESSAGE_GAME_START + MESSAGE_TURN.format(STONE_TYPE_BLACK))
    }

    private fun gameRestart() {
        tableLayoutBoard.forEach { it.setImageResource(0) }
        omokDao.deleteAllStones()
        gameStart()
    }

    private fun progressGameTurn(
        point: Point,
        view: ImageView,
    ) {
        val stone = Stone(turn.stoneType, point)
        val nextTurn = board.putStone(stone, turn, ruleAdapter)
        displayMessage(generateTurnMessage(nextTurn, board.beforeStone))
        if (turn != nextTurn) {
            view.setImageResource(getStoneImage(turn.stoneType))
            omokDao.insertStone(stone)
            turn = nextTurn
        } else {
            displayMessage(MESSAGE_INVALID_POINT_INPUT)
        }
    }

    private fun getStoneImage(stoneType: StoneType): Int {
        return when (stoneType) {
            StoneType.BLACK -> R.drawable.black_stone
            StoneType.WHITE -> R.drawable.white_stone
            StoneType.EMPTY -> 0
        }
    }

    private fun judgeGameState(): Boolean {
        if (turn is FinishedTurn) {
            displayMessage(MESSAGE_WINNER.format(generateWinnerMessage(board.beforeStone?.type)))
            return false
        }
        return true
    }

    private fun displayMessage(message: String) {
        toast?.cancel()
        toast = Toast.makeText(this, message, Toast.LENGTH_SHORT)
        toast?.show()
    }

    companion object {
        private const val BOARD_SIZE = 15
    }
}
