package woowacourse.omok.view

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import woowacourse.omok.R
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
    private val currentTurnTextView: TextView by lazy { findViewById(R.id.tv_current_turn) }
    private lateinit var gameState: GameState

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_omok_game)

        val gameId = intent.getLongExtra(GAME_ID, 0)
        val gameTitle = intent.getStringExtra(GAME_TITLE)
        val placedIndexItems = placementDao.findAll(gameId).map { it.index }

        initializeGameTitle(gameTitle)
        initializeBoard(placedIndexItems, gameId)
        setCurrentTurnText()
    }

    private fun initializeGameTitle(gameTitle: String?) {
        findViewById<TextView>(R.id.tv_name).text = gameTitle
    }

    private fun initializeBoard(
        placedIndexItems: List<Int>,
        gameId: Long,
    ) {
        val boardItems =
            findViewById<TableLayout>(R.id.board)
                .children
                .filterIsInstance<TableRow>()
                .flatMap { it.children }
                .filterIsInstance<ImageView>()

        initializeBoardPlacementView(placedIndexItems, boardItems)
        boardItems.forEachIndexed { flattenedIndex, view ->
            view.setOnClickListener {
                markPosition(flattenedIndex, view, gameId)
            }
        }
    }

    private fun initializeBoardPlacementView(
        placedIndexItems: List<Int>,
        boardItems: Sequence<ImageView>,
    ) {
        placedIndexItems.forEach {
            setGameState(Position(it))
            setStoneImage(boardItems.toList()[it])
        }
    }

    private fun markPosition(
        flattenedIndex: Int,
        view: ImageView,
        gameId: Long,
    ) {
        if (!::gameState.isInitialized || gameState !is GameState.GameOver) {
            setGameState(Position(flattenedIndex))
            if (gameState !is GameState.Error) {
                val currentColor = placementData.lastTurnColor ?: Color.BLACK
                setStoneImage(view)
                savePlacementInfo(gameId, flattenedIndex, currentColor)
                setCurrentTurnText()
            }
        }
    }

    private fun savePlacementInfo(
        gameId: Long,
        flattenedIndex: Int,
        color: Color,
    ) {
        placementDao.save(
            Placement(
                gameId = gameId,
                color = color.name,
                index = flattenedIndex,
            ),
        )
    }

    private fun setCurrentTurnText() {
        val currentColor =
            placementData.lastTurnColor?.let { Color.getReversedColor(it) } ?: Color.BLACK
        currentTurnTextView.text = getString(R.string.message_turn).format(currentColor)
    }

    private fun setGameState(position: Position) {
        gameState = playEachTurn(position)
        showGameStateMessage(gameState)
        if (gameState is GameState.GameOver) {
            currentTurnTextView.visibility = View.INVISIBLE
        }
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
            is GameState.GameOver -> {
                val resultMessage = generateResultMessage(gameState)
                showToastMessage(resultMessage)
            }
            is GameState.OnProgress -> return
            is GameState.Error -> {
                gameState.message.also {
                    showToastMessage(it)
                }
            }
        }
    }

    private fun generateResultMessage(gameState: GameState.GameOver): String =
        when (gameState.gameResult) {
            GameResult.DRAW -> getString(R.string.message_draw)
            else -> getString(R.string.message_winner).format(gameState.gameResult.label)
        }

    private fun playEachTurn(position: Position): GameState = placementData.place(position)

    private fun showToastMessage(message: String) = Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

    companion object {
        private const val GAME_ID = "game_id"
        private const val GAME_TITLE = "game_title"
    }
}
