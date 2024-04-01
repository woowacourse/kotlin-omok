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
import woowacourse.omok.db.OmokEntity
import woowacourse.omok.domain.model.FinishedTurn
import woowacourse.omok.domain.model.OmokGame
import woowacourse.omok.domain.model.Point
import woowacourse.omok.domain.model.StoneType
import woowacourse.omok.domain.model.StoneType.Companion.getStoneTypeByIndex
import woowacourse.omok.domain.view.OutputView.MESSAGE_GAME_END
import woowacourse.omok.domain.view.OutputView.MESSAGE_GAME_START
import woowacourse.omok.domain.view.OutputView.MESSAGE_INVALID_POINT_INPUT
import woowacourse.omok.domain.view.OutputView.MESSAGE_TURN
import woowacourse.omok.domain.view.OutputView.MESSAGE_WINNER
import woowacourse.omok.domain.view.OutputView.STONE_TYPE_BLACK
import woowacourse.omok.domain.view.OutputView.generateStoneTypeMessage
import woowacourse.omok.domain.view.OutputView.generateTurnMessage

class MainActivity : AppCompatActivity() {
    private val omokGame: OmokGame = OmokGame(BOARD_SIZE)
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
    private var toast: Toast? = null

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
        if (!omokGame.gameState) {
            displayMessage(MESSAGE_GAME_END)
            return
        }
        setStoneOnView(point, view)
        omokGame.updateGameState(judgeGameState())
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
            val point = Point(stone.pointX, stone.pointY)
            omokGame.updateTurn(omokGame.board.putStone(point, omokGame.turn, omokGame.ruleAdapter))
            omokGame.updateBeforePoint(point)
            val coordinate = (BOARD_SIZE - stone.pointY - 1) * BOARD_SIZE + stone.pointX
            tableLayoutBoard[coordinate].setImageResource(getStoneImage(getStoneTypeByIndex(index)))
        }
    }

    private fun setUpGameState() {
        if (omokGame.beforePoint != null) {
            omokGame.updateGameState(
                !omokGame.ruleAdapter.checkWin(
                    omokGame.beforePoint!!,
                    omokGame.turn.stoneType,
                ),
            )
        }
        if (omokGame.gameState) {
            displayMessage(generateTurnMessage(omokGame.turn, omokGame.beforePoint))
        } else {
            displayMessage(MESSAGE_GAME_END)
        }
    }

    private fun gameRestart() {
        omokGame.gameReSet()
        tableLayoutBoard.forEach { it.setImageResource(0) }
        omokDao.deleteAllStones()
        displayMessage(MESSAGE_GAME_START + MESSAGE_TURN.format(STONE_TYPE_BLACK))
    }

    private fun setStoneOnView(
        point: Point,
        view: ImageView,
    ) {
        val nextTurn = omokGame.board.putStone(point, omokGame.turn, omokGame.ruleAdapter)
        omokGame.updateBeforePoint(point)
        displayMessage(generateTurnMessage(nextTurn, omokGame.beforePoint))
        if (omokGame.turn != nextTurn) {
            view.setImageResource(getStoneImage(omokGame.turn.stoneType))
            omokDao.insertStone(OmokEntity(point.x, point.y))
            omokGame.updateTurn(nextTurn)
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
        if (omokGame.turn is FinishedTurn) {
            displayMessage(MESSAGE_WINNER.format(generateStoneTypeMessage(omokGame.turn.stoneType)))
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
