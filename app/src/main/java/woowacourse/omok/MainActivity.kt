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
import woowacourse.omok.db.StoneEntity
import woowacourse.omok.domain.model.BlackTurn
import woowacourse.omok.domain.model.Board
import woowacourse.omok.domain.model.FinishedTurn
import woowacourse.omok.domain.model.Point
import woowacourse.omok.domain.model.RuleAdapter
import woowacourse.omok.domain.model.StoneType
import woowacourse.omok.domain.model.StoneType.Companion.getStoneTypeByIndex
import woowacourse.omok.domain.model.Turn
import woowacourse.omok.domain.view.OutputView.MESSAGE_GAME_END
import woowacourse.omok.domain.view.OutputView.MESSAGE_GAME_START
import woowacourse.omok.domain.view.OutputView.MESSAGE_INVALID_POINT_INPUT
import woowacourse.omok.domain.view.OutputView.MESSAGE_TURN
import woowacourse.omok.domain.view.OutputView.MESSAGE_WINNER
import woowacourse.omok.domain.view.OutputView.STONE_TYPE_BLACK
import woowacourse.omok.domain.view.OutputView.generateStoneTypeMessage
import woowacourse.omok.domain.view.OutputView.generateTurnMessage

class MainActivity : AppCompatActivity() {
    private var board: Board = Board(BOARD_SIZE)
    private var ruleAdapter: RuleAdapter = RuleAdapter(board)
    private var turn: Turn = BlackTurn()
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

        gameSetting()
    }

    private fun gameSetting() {
        setUpTableLayoutBoard()
        setUpRestartButton()
        setUpBoardUi()
        setUpGameState()
    }

    private fun setUpTableLayoutBoard() {
        tableLayoutBoard.forEachIndexed { index, view ->
            val x = index % BOARD_SIZE
            val y = BOARD_SIZE - 1 - (index / BOARD_SIZE)
            view.setOnClickListener {
                handleGameTurn(Point(x, y), view)
            }
        }
    }

    private fun handleGameTurn(
        point: Point,
        view: ImageView,
    ) {
        if (!onGame) {
            displayMessage(MESSAGE_GAME_END)
            return
        }
        setStoneOnView(point, view)
        onGame = judgeGameState()
    }

    private fun setUpRestartButton() {
        val reStartButton: Button = findViewById(R.id.reStartButton)
        reStartButton.setOnClickListener {
            gameRestart()
        }
    }

    private fun setUpBoardUi() {
        val stones = omokDao.findAllStones()
        stones.withIndex().forEach { (index, stone) ->
            turn = board.putStone(Point(stone.pointX, stone.pointY), turn, ruleAdapter)
            val coordinate = (BOARD_SIZE - stone.pointY - 1) * BOARD_SIZE + stone.pointX
            tableLayoutBoard[coordinate].setImageResource(getStoneImage(getStoneTypeByIndex(index)))
        }
    }

    private fun setUpGameState() {
        val beforeStone = board.beforePoint
        if (beforeStone != null) {
            onGame = !ruleAdapter.checkWin(beforeStone, turn.stoneType)
        }
        if (onGame) {
            displayMessage(MESSAGE_GAME_START + MESSAGE_TURN.format(generateStoneTypeMessage(turn.stoneType)))
        } else {
            displayMessage(MESSAGE_GAME_END)
        }
    }

    private fun gameRestart() {
        board = Board(BOARD_SIZE)
        ruleAdapter = RuleAdapter(board)
        turn = BlackTurn()
        onGame = true
        tableLayoutBoard.forEach { it.setImageResource(0) }
        omokDao.deleteAllStones()
        displayMessage(MESSAGE_GAME_START + MESSAGE_TURN.format(STONE_TYPE_BLACK))
    }

    private fun setStoneOnView(
        point: Point,
        view: ImageView,
    ) {
        val nextTurn = board.putStone(point, turn, ruleAdapter)
        displayMessage(generateTurnMessage(nextTurn, board.beforePoint))
        if (turn != nextTurn) {
            view.setImageResource(getStoneImage(turn.stoneType))
            omokDao.insertStone(StoneEntity(point.x, point.y))
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
            displayMessage(MESSAGE_WINNER.format(generateStoneTypeMessage(turn.stoneType)))
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
