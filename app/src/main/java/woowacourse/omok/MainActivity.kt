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
import woowacourse.omok.domain.model.OmokGame
import woowacourse.omok.domain.model.OmokGame.Companion.BOARD_SIZE
import woowacourse.omok.domain.model.Point
import woowacourse.omok.domain.model.StoneType
import woowacourse.omok.domain.model.StoneType.Companion.getStoneTypeByName
import woowacourse.omok.domain.view.OutputView.MESSAGE_GAME_END
import woowacourse.omok.domain.view.OutputView.MESSAGE_GAME_START
import woowacourse.omok.domain.view.OutputView.MESSAGE_INVALID_POINT_INPUT
import woowacourse.omok.domain.view.OutputView.MESSAGE_TURN
import woowacourse.omok.domain.view.OutputView.MESSAGE_WINNER
import woowacourse.omok.domain.view.OutputView.STONE_TYPE_BLACK
import woowacourse.omok.domain.view.OutputView.generateStoneTypeMessage
import woowacourse.omok.domain.view.OutputView.generateTurnMessage

class MainActivity : AppCompatActivity() {
    private val omokGame: OmokGame = OmokGame()
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
        setUpBoard()
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
        if (!omokGame.onGame()) {
            displayMessage(MESSAGE_GAME_END)
            return
        }
        setStoneOnView(point, view)
    }

    private fun setUpRestartButton() {
        val reStartButton: Button = findViewById(R.id.reStartButton)
        reStartButton.setOnClickListener {
            gameRestart()
        }
    }

    private fun setUpBoard() {
        val omokEntities = omokDao.findAllStones()
        if (omokEntities.isNotEmpty()) {
            omokGame.initBoard(omokEntities)
            omokEntities.forEach { omokEntity ->
                val index = (BOARD_SIZE - omokEntity.pointY - 1) * BOARD_SIZE + omokEntity.pointX
                tableLayoutBoard[index].setImageResource(getStoneImage(getStoneTypeByName(omokEntity.stoneTypeName)))
            }
        }
        if (omokGame.onGame()) {
            displayMessage(
                MESSAGE_GAME_START +
                    MESSAGE_TURN.format(
                        generateStoneTypeMessage(
                            omokGame.turn.stoneType,
                        ),
                    ),
            )
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
        if (omokGame.putStone(point)) {
            view.setImageResource(getStoneImage(omokGame.board.getLastStoneType()))
            omokDao.insertStone(
                OmokEntity(
                    omokGame.board.getLastStoneType().name,
                    point.x,
                    point.y,
                ),
            )
            if (omokGame.onGame()) {
                displayMessage(generateTurnMessage(omokGame.turn, omokGame.board.beforePoint))
            } else {
                displayMessage(MESSAGE_WINNER.format(generateStoneTypeMessage(omokGame.turn.stoneType)))
            }
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

    private fun displayMessage(message: String) {
        toast?.cancel()
        toast = Toast.makeText(this, message, Toast.LENGTH_SHORT)
        toast?.show()
    }
}
