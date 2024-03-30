package woowacourse.omok

import android.os.Bundle
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import woowacourse.omok.model.Board
import woowacourse.omok.model.Color
import woowacourse.omok.model.GameResult
import woowacourse.omok.model.Position
import woowacourse.omok.model.Stone
import woowacourse.omok.model.database.Placement
import woowacourse.omok.model.database.PlacementDao
import woowacourse.omok.model.state.GameState

class OmokGameActivity : AppCompatActivity() {
    private val placementData: Board by lazy { Board() }
    private val placementDao: PlacementDao by lazy { PlacementDao(this) }
    private lateinit var gameState: GameState
    private lateinit var currentTurn: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_omok_game)

        currentTurn = findViewById(R.id.tv_current_turn)

        val gameId = intent.getLongExtra(GAME_ID, 0)
        val gameTitle = intent.getStringExtra(GAME_TITLE)
        val placedIndexItems = placementDao.findAll(gameId).map { it.index }

        initializeGameTitle(gameTitle)
        initializeBoard(placedIndexItems, gameId)
        setCurrentTurnText()
    }

    private fun setCurrentTurnText() {
        val currentColor =
            placementData.lastPlacement?.color?.let { Color.getReversedColor(it) } ?: Color.BLACK
        currentTurn.text = getString(R.string.message_turn).format(currentColor)
    }

    private fun initializeGameTitle(gameTitle: String?) {
        findViewById<TextView>(R.id.tv_name).text = gameTitle
    }

    private fun initializeBoard(
        placedIndexItems: List<Int>,
        gameId: Long,
    ) {
        findViewById<TableLayout>(R.id.board)
            .children
            .filterIsInstance<TableRow>()
            .flatMap { it.children }
            .filterIsInstance<ImageView>()
            .forEachIndexed { index, view ->
                if (index in placedIndexItems) {
                    setGameState(index)
                    setStoneImage(view)
                }
                view.setOnClickListener {
                    markPosition(index, view, gameId)
                }
            }
    }

    private fun markPosition(
        index: Int,
        view: ImageView,
        gameId: Long,
    ) {
        if (!::gameState.isInitialized || gameState !is GameState.GameOver) {
            setGameState(index)
            if (gameState !is GameState.Error) {
                setStoneImage(view)
                savePlacementInfo(gameId, index)
                setCurrentTurnText()
            }
        }
    }

    private fun savePlacementInfo(
        gameId: Long,
        index: Int,
    ) {
        placementDao.save(
            Placement(
                gameId = gameId,
                color = placementData.lastPlacement?.color?.name,
                index = index,
            ),
        )
    }

    private fun setGameState(index: Int) {
        val position = getInputPosition(index)
        gameState = playEachTurn(position)
        showGameStateMessage(gameState)
    }

    private fun setStoneImage(view: ImageView) {
        when (placementData.lastPlacement) {
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

    private fun generateResultMessage(gameState: GameState.GameOver): String =
        when (gameState.gameResult) {
            GameResult.DRAW -> getString(R.string.message_draw)
            else -> getString(R.string.message_winner).format(gameState.gameResult.label)
        }

    private fun playEachTurn(position: Pair<Int, Int>): GameState = placementData.place(Position(position.first, position.second))

    private fun getInputPosition(index: Int): Pair<Int, Int> {
        val row = index / BOARD_DISPLAY_SIZE + 1
        val column = index % BOARD_DISPLAY_SIZE + 1
        return Pair(row, column)
    }

    private fun showToastMessage(message: String) = Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

    companion object {
        private const val BOARD_DISPLAY_SIZE = 15
        private const val GAME_ID = "game_id"
        private const val GAME_TITLE = "game_title"
    }
}
