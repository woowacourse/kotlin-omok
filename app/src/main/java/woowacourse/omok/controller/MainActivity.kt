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
import woowacourse.omok.db.OmokRepository
import woowacourse.omok.model.game.AndroidOmokGame
import woowacourse.omok.model.omok.Board
import woowacourse.omok.model.omok.OmokStone
import woowacourse.omok.model.omok.Position
import woowacourse.omok.view.OmokBoardView
import woowacourse.omok.view.OmokOutputView
import woowacourse.omok.view.OmokTextView

class MainActivity : AppCompatActivity() {
    private lateinit var omokBoardView: OmokBoardView
    private lateinit var omokTextView: OmokOutputView
    private val omokRepository by lazy { OmokRepository(this) }
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
        omokRepository.save(stone)
    }

    private fun handleGameFinish(
        board: Board,
        stone: OmokStone,
    ) {
        omokTextView.showGameResult(board, stone)
        omokRepository.delete()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initializeOmokViewComponents()
        reloadGameFromStorage()
    }

    private fun initializeOmokViewComponents() {
        omokBoardView = OmokBoardView(setupBoardImageViews())
        omokBoardView.setupClickListener(game::placeOmokStone)
        omokTextView = OmokTextView(setupTextView())
        setupResetButtonClickListener()
    }

    private fun setupBoardImageViews(): List<ImageView> =
        findViewById<TableLayout>(R.id.board)
            .children
            .filterIsInstance<TableRow>()
            .flatMap { it.children }
            .filterIsInstance<ImageView>()
            .toList()

    private fun setupTextView(): TextView = findViewById(R.id.tv_turn_information)

    private fun setupResetButtonClickListener() {
        val resetButton = findViewById<Button>(R.id.btn_game_reset)
        resetButton.setOnClickListener {
            omokBoardView.resetView()
            omokTextView.resetView()
            omokRepository.delete()
            game.resetGame()
        }
    }

    private fun reloadGameFromStorage() {
        omokRepository.findSavedBoard().run {
            lastStone?.let { lastStone ->
                game.restoreGame(lastStone.color, this)
                omokBoardView.updateBoard(this)
                omokTextView.showProgress(lastStone)
            }
        }
    }
}
