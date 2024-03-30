package woowacourse.omok

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import woowacourse.omok.db.OmokDbHelper
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
    private var toast: Toast? = null
    private var onGame: Boolean = true
    private val omokDbHelper: OmokDbHelper by lazy { OmokDbHelper(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tableLayoutBoard: TableLayout = findViewById(R.id.board)
        val reStartButton: Button = findViewById(R.id.reStartButton)

        gameStart(tableLayoutBoard)

        reStartButton.setOnClickListener {
            gameRestart(tableLayoutBoard)
        }
    }

    private fun gameStart(tableLayoutBoard: TableLayout) {
        board = Board(15)
        ruleAdapter = RuleAdapter(board)
        turn = BlackTurn()
        onGame = true

        displayMessage(MESSAGE_GAME_START + MESSAGE_TURN.format(STONE_TYPE_BLACK))

        tableLayoutBoard
            .children
            .filterIsInstance<TableRow>()
            .forEachIndexed { x, rows ->
                rows.children.filterIsInstance<ImageView>()
                    .forEachIndexed { y, view ->
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
    }

    private fun gameRestart(tableLayoutBoard: TableLayout) {
        tableLayoutBoard
            .children
            .filterIsInstance<TableRow>()
            .forEach { row ->
                row.children.filterIsInstance<ImageView>()
                    .forEach { view ->
                        view.setImageResource(0)
                    }
            }
        gameStart(tableLayoutBoard)
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
            omokDbHelper.insertStone(stone)
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
}
