package woowacourse.omok.controller

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import woowacourse.omok.R
import woowacourse.omok.db.OmokEntry
import woowacourse.omok.db.OmokEntryDao
import woowacourse.omok.model.Board
import woowacourse.omok.model.Position
import woowacourse.omok.model.rule.RenjuRule
import woowacourse.omok.model.state.BlackTurn
import woowacourse.omok.model.state.GameState
import woowacourse.omok.util.convertIndexToPosition
import woowacourse.omok.util.mapEntriesToBoard
import woowacourse.omok.util.mapStoneColorToGameState
import woowacourse.omok.util.mapStoneColorToString
import woowacourse.omok.util.showToast
import woowacourse.omok.view.BoardView

class MainActivity : AppCompatActivity() {
    private var gameState: GameState = BlackTurn(RenjuRule, Board(emptyMap()))
    private val dbDao by lazy { OmokEntryDao(this) }
    private lateinit var boardView: BoardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        boardView = BoardView(setupBoardView())
        restoreSavedGame()
        initBoardView()
        initGameResetButton()
    }

    private fun setupBoardView(): List<ImageView> =
        findViewById<TableLayout>(R.id.board)
            .children
            .filterIsInstance<TableRow>()
            .flatMap { it.children }
            .filterIsInstance<ImageView>()
            .toList()

    private fun restoreSavedGame() {
        dbDao.findAll().takeIf { it.isNotEmpty() }?.let { omokEntries ->
            val savedBoard = mapEntriesToBoard(omokEntries)
            restoreModelAndView(savedBoard)
        }
    }

    private fun restoreModelAndView(board: Board) {
        board.lastStone?.let { lastStone ->
            gameState = mapStoneColorToGameState(lastStone.color, board)
            boardView.updateBoard(gameState.board)
        }
    }

    private fun initBoardView() {
        boardView.spaceViews.forEachIndexed { index, spaceView ->
            spaceView.setOnClickListener {
                placeOmokStone(convertIndexToPosition(index))
            }
        }
    }

    private fun placeOmokStone(position: Position) {
        runCatching {
            val newState = gameState.put(position)
            if (gameState != newState) {
                gameState = newState
                updateViewAndDb(position)
            }
        }.onFailure {
            showToast(this, "${it.message}")
        }
    }

    private fun updateViewAndDb(position: Position) {
        gameState.board[position]?.run {
            boardView.updateSingleSpace(this)
            dbDao.save(
                OmokEntry(
                    x = this.position.x,
                    y = this.position.y,
                    color = mapStoneColorToString(this.color),
                ),
            )
        }
    }

    private fun initGameResetButton() {
        val resetButton = findViewById<Button>(R.id.btn_game_reset)
        resetButton.setOnClickListener {
            boardView.resetView()
            dbDao.drop()
            gameState = BlackTurn(RenjuRule, Board(emptyMap()))
        }
    }
}
