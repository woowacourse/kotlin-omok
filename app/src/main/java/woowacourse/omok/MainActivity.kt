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
import woowacourse.omok.domain.model.Board.Companion.BOARD_SIZE
import woowacourse.omok.domain.model.OmokGame
import woowacourse.omok.domain.model.Point
import woowacourse.omok.domain.model.Point.Companion.MESSAGE_INVALID_POINT_INPUT
import woowacourse.omok.domain.model.StoneType
import woowacourse.omok.domain.model.Turn
import woowacourse.omok.domain.view.OutputView.MESSAGE_GAME_END
import woowacourse.omok.domain.view.OutputView.generateTurnMessage

class MainActivity : AppCompatActivity() {
    private lateinit var omokGame: OmokGame
    private val omokDb by lazy { OmokDao(this) }
    private val boardUi: List<ImageView> by lazy {
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

        setupBoardUi()
        setupRestartButton()
        initializeBoardSetting()
    }

    private fun setupBoardUi() {
        boardUi.forEachIndexed { index, view ->
            view.setOnClickListener {
                if (omokGame.isGameFinished()) {
                    displayMessage(MESSAGE_GAME_END)
                    return@setOnClickListener
                }
                val x = index % BOARD_SIZE
                val y = index / BOARD_SIZE
                progressGameTurn(view, x, y)
            }
        }
    }

    private fun setupRestartButton() {
        val restartButton = findViewById<Button>(R.id.btn_restart)
        restartButton.setOnClickListener {
            boardUi.forEach { it.setImageResource(0) }
            omokDb.resetGame()
            omokGame = OmokGame()
        }
    }

    private fun initializeBoardSetting() {
        val stones = omokDb.getStonesFromDatabase().getOrDefault(emptyList())
        stones.forEach { (stoneType, point) ->
            val coordinate = point.y * BOARD_SIZE + point.x
            boardUi[coordinate].setImageResource(getStoneImage(stoneType))
        }
        initializeGameSetting(Board(stones))
    }

    private fun initializeGameSetting(initialBoard: Board) {
        val initialTurn = runCatching { Turn.determineTurn(initialBoard) }.getOrDefault(BlackTurn())
        omokGame = OmokGame(turn = initialTurn, board = initialBoard)
        displayMessage(generateTurnMessage(initialTurn))
    }

    private fun progressGameTurn(
        view: ImageView,
        x: Int,
        y: Int,
    ) {
        val isSuccess =
            omokGame.tryPlayTurn(
                updateBoard = { view.setImageResource(getStoneImage(it.latestStone?.type)) },
                updateTurn = { turn, stone ->
                    displayMessage(generateTurnMessage(turn))
                    stone?.let { omokDb.saveStone(it) }
                },
                getPoint = { Point(x, y) },
            )
        if (!isSuccess) displayMessage(MESSAGE_INVALID_POINT_INPUT)
    }

    private fun getStoneImage(stoneType: StoneType?): Int =
        when (stoneType) {
            StoneType.BLACK -> R.drawable.black_stone
            StoneType.WHITE -> R.drawable.white_stone
            StoneType.EMPTY -> 0
            null -> 0
        }

    private fun displayMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
