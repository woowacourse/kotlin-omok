package woowacourse.omok

import android.content.ContentValues
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import woowacourse.omok.db.OmokContract
import woowacourse.omok.db.OmokDbHelper
import woowacourse.omok.domain.model.BlackTurn
import woowacourse.omok.domain.model.Board
import woowacourse.omok.domain.model.Board.Companion.BOARD_SIZE
import woowacourse.omok.domain.model.FinishedTurn
import woowacourse.omok.domain.model.OmokGame
import woowacourse.omok.domain.model.Point
import woowacourse.omok.domain.model.Point.Companion.MESSAGE_INVALID_POINT_INPUT
import woowacourse.omok.domain.model.Stone
import woowacourse.omok.domain.model.StoneType
import woowacourse.omok.domain.model.Turn
import woowacourse.omok.domain.model.WhiteTurn
import woowacourse.omok.domain.view.OutputView.MESSAGE_GAME_END
import woowacourse.omok.domain.view.OutputView.generateTurnMessage

class MainActivity : AppCompatActivity() {
    private lateinit var omokGame: OmokGame
    private val omokDb by lazy { OmokDbHelper(this).writableDatabase }
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
            val x = index % BOARD_SIZE
            val y = index / BOARD_SIZE
            view.setOnClickListener {
                if (omokGame.isGameFinished()) {
                    displayMessage(MESSAGE_GAME_END)
                    return@setOnClickListener
                }
                progressGameTurn(view, x, y)
            }
        }
    }

    private fun setupRestartButton() {
        val restartButton = findViewById<Button>(R.id.btn_restart)
        restartButton.setOnClickListener {
            boardUi.forEach { it.setImageResource(0) }
            omokDb.delete(OmokContract.TABLE_NAME, null, null)
            omokGame = OmokGame()
        }
    }

    private fun initializeBoardSetting() {
        val projection = arrayOf(OmokContract.STONE_TYPE, OmokContract.POINT_X, OmokContract.POINT_Y)
        val cursor =
            omokDb.query(OmokContract.TABLE_NAME, projection, null, null, null, null, null)
        val initialBoard = Board()
        var latestStoneType = StoneType.WHITE

        with(cursor) {
            while (moveToNext()) {
                val stoneTypeValue = getInt(cursor.getColumnIndexOrThrow(OmokContract.STONE_TYPE))
                val pointX = getInt(cursor.getColumnIndexOrThrow(OmokContract.POINT_X))
                val pointY = getInt(cursor.getColumnIndexOrThrow(OmokContract.POINT_Y))
                val coordinate = pointY * BOARD_SIZE + pointX

                latestStoneType = StoneType.fromValue(stoneTypeValue)
                boardUi[coordinate].setImageResource(getStoneImage(latestStoneType))
                initialBoard.putStone(Stone(latestStoneType, Point(pointX, pointY)))
            }
        }
        cursor.close()
        initializeGameSetting(initialBoard, latestStoneType)
    }

    private fun initializeGameSetting(
        initialBoard: Board,
        latestStoneType: StoneType,
    ) {
        val isGameEnd = initialBoard.latestStone?.let { initialBoard.isWinCondition(it) } ?: false
        val initialTurn = if (isGameEnd) FinishedTurn(initialBoard.latestStone!!) else createTurn(latestStoneType)

        omokGame = OmokGame(turn = initialTurn, board = initialBoard)
        displayMessage(generateTurnMessage(initialTurn))
    }

    private fun createTurn(stoneType: StoneType?): Turn {
        return when (stoneType) {
            StoneType.BLACK -> WhiteTurn()
            StoneType.WHITE -> BlackTurn()
            StoneType.EMPTY -> throw IllegalStateException()
            null -> throw IllegalStateException()
        }
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
                    stone?.let { saveStoneData(it) }
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

    private fun saveStoneData(stone: Stone) {
        val newStone =
            ContentValues().apply {
                put(OmokContract.STONE_TYPE, stone.type.value)
                put(OmokContract.POINT_X, stone.point.x)
                put(OmokContract.POINT_Y, stone.point.y)
            }
        omokDb.insert(OmokContract.TABLE_NAME, null, newStone)
    }

    private fun displayMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
