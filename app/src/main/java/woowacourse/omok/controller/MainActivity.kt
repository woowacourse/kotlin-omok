package woowacourse.omok.controller

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import woowacourse.omok.R
import woowacourse.omok.db.OmokEntry
import woowacourse.omok.db.OmokEntryDao
import woowacourse.omok.model.AndroidOmokGame
import woowacourse.omok.model.Board
import woowacourse.omok.model.OmokStone
import woowacourse.omok.model.Position
import woowacourse.omok.model.StoneColor
import woowacourse.omok.util.mapStoneColorToString
import woowacourse.omok.util.showToast
import woowacourse.omok.view.OmokBoardView
import woowacourse.omok.view.OmokOutputView
import woowacourse.omok.view.OmokTextView

class MainActivity : AppCompatActivity() {
    private lateinit var omokBoardView: OmokBoardView
    private lateinit var omokTextView: OmokOutputView
    private val dbDao by lazy { OmokEntryDao(this) }
    private val game by lazy {
        AndroidOmokGame(
            onStateChanged = ::handleGameStateChange,
            onFinishGame = ::handleGameFinish,
        )
    }

    private fun handleGameStateChange(position: Position) {
        val stone = game.getStoneByPosition(position)
        stone?.let { newStone ->
            addStoneToViewAndDatabase(newStone)
        }
    }

    private fun addStoneToViewAndDatabase(stone: OmokStone) {
        omokBoardView.updateSingleSpace(stone)
        omokTextView.showProgress(stone)
        dbDao.save(
            OmokEntry(
                x = stone.position.x,
                y = stone.position.y,
                color = mapStoneColorToString(stone.color),
            ),
        )
    }

    private fun handleGameFinish(
        board: Board,
        stone: OmokStone,
    ) {
        omokTextView.showGameResult(board, stone)
        dbDao.delete()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initializeOmokView()
        initializeGameResetButton()
        restoreSavedGame()
    }

    private fun initializeOmokView() {
        omokBoardView = OmokBoardView(initializeImageViews())
        omokBoardView.setupClickListener(::placeOmokStone)
        omokTextView = OmokTextView(initializeTextView())
    }

    private fun initializeImageViews(): List<ImageView> =
        findViewById<TableLayout>(R.id.board)
            .children
            .filterIsInstance<TableRow>()
            .flatMap { it.children }
            .filterIsInstance<ImageView>()
            .toList()

    private fun placeOmokStone(position: Position) {
        game.placeOmokStone(position) { showToast(this, "게임이 이미 종료되었습니다.") }
    }

    private fun initializeTextView(): TextView = findViewById(R.id.tv_turn_information)

    private fun restoreSavedGame() {
        dbDao.findAll().takeIf { it.isNotEmpty() }?.let { omokEntries ->
            val savedBoard = mapEntriesToBoard(omokEntries)
            restoreModelAndView(savedBoard)
        }
    }

    private fun mapEntriesToBoard(omokEntries: List<OmokEntry>): Board {
        return Board(
            omokEntries.map { item ->
                OmokStone(
                    position = Position.of(item.x, item.y),
                    color = mapStringToStoneColor(item.color),
                )
            }.associateBy(OmokStone::position),
        )
    }

    private fun mapStringToStoneColor(colorString: String): StoneColor {
        return when (colorString) {
            "흑" -> StoneColor.BLACK
            "백" -> StoneColor.WHITE
            else -> throw IllegalArgumentException()
        }
    }

    private fun restoreModelAndView(board: Board) {
        board.lastStone?.let { lastStone ->
            game.restoreGame(lastStone.color, board)
            omokBoardView.updateBoard(board)
            omokTextView.showProgress(lastStone)
        }
    }

    private fun initializeGameResetButton() {
        val resetButton = findViewById<Button>(R.id.btn_game_reset)
        resetButton.setOnClickListener {
            omokBoardView.resetView()
            omokTextView.resetView()
            dbDao.delete()
            game.resetGame()
        }
    }
}
