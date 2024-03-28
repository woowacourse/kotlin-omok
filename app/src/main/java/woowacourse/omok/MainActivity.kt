package woowacourse.omok

import android.os.Bundle
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import woowacourse.omok.model.Board
import woowacourse.omok.model.GameResult
import woowacourse.omok.model.Position
import woowacourse.omok.model.Stone
import woowacourse.omok.model.state.GameState

class MainActivity : AppCompatActivity() {
    private val domainBoard: Board by lazy { Board() }
    private lateinit var gameState: GameState

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val board = findViewById<TableLayout>(R.id.board)
        board
            .children
            .filterIsInstance<TableRow>()
            .flatMap { it.children }
            .filterIsInstance<ImageView>()
            .forEachIndexed { index, view ->
                view.setOnClickListener {
                    markPosition(index, view)
                }
            }
    }

    private fun markPosition(
        index: Int,
        view: ImageView,
    ) {
        val position = getInputPosition(index)
        if (!::gameState.isInitialized || gameState !is GameState.GameOver) {
            gameState = playEachTurn(position)
            showGameStateMessage(gameState)
            if (gameState !is GameState.Error) setStoneImage(view)
        }
    }

    private fun setStoneImage(view: ImageView) {
        when (domainBoard.lastPlacement) {
            is Stone.Black -> view.setImageResource(R.drawable.black_stone)
            is Stone.White -> view.setImageResource(R.drawable.white_stone)
            null -> view.setImageResource(R.drawable.black_stone)
        }
    }

    private fun showGameStateMessage(gameState: GameState) {
        when (gameState) {
            is GameState.GameOver -> showToastMessage(generateResultMessage(gameState))
            is GameState.OnProgress -> return
            is GameState.Error -> showToastMessage(gameState.message)
        }
    }

    private fun generateResultMessage(gameState: GameState.GameOver) =
        when (gameState.gameResult) {
            GameResult.DRAW -> MESSAGE_DRAW
            else -> MESSAGE_WINNER.format(gameState.gameResult.label)
        }

    private fun playEachTurn(position: Pair<Int, Int>): GameState {
        return domainBoard.place(Position(position.first, position.second))
    }

    private fun getInputPosition(index: Int): Pair<Int, Int> {
        val row = index / BOARD_DISPLAY_SIZE + 1
        val column = index % BOARD_DISPLAY_SIZE + 1
        return Pair(row, column)
    }

    private fun showToastMessage(message: String) = Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

    companion object {
        private const val BOARD_DISPLAY_SIZE = 15
        private const val MESSAGE_WINNER = "%s이 이겼습니다."
        private const val MESSAGE_DRAW = "무승부입니다."
    }
}
