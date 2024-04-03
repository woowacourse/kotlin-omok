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
        val placedIndexItems =
            placementDao.findAll(gameId).map {
                Position(it.horizontalCoordinate, it.verticalCoordinate)
            }

        initializeGameTitle(gameTitle)
        initializeBoard(placedIndexItems, gameId)
        setCurrentTurnText()
    }

    private fun initializeGameTitle(gameTitle: String?) {
        findViewById<TextView>(R.id.tv_name).text = gameTitle
    }

    private fun initializeBoard(
        placedIndexItems: List<Position>,
        gameId: Long,
    ) {
        val board =
            findViewById<TableLayout>(R.id.board)
                .children
                .filterIsInstance<TableRow>()

        val imageViews =
            board
                .flatMap { it.children }
                .filterIsInstance<ImageView>()

        initializeBoardView(placedIndexItems, imageViews)

        board.forEachIndexed { rowIndex, tableRow ->
            val boardItems = tableRow.children.filterIsInstance<ImageView>()
            setRowItems(boardItems, rowIndex, gameId)
        }
    }

    private fun initializeBoardView(
        placedIndexItems: List<Position>,
        imageViews: Sequence<ImageView>,
    ) {
        placedIndexItems.forEach {
            setGameState(it)
            setStoneImage(
                imageViews.toList()[(BOARD_DISPLAY_SIZE - it.horizontalCoordinate) * BOARD_DISPLAY_SIZE + it.verticalCoordinate - 1],
            )
        }
    }

    private fun setRowItems(
        boardItems: Sequence<ImageView>,
        rowIndex: Int,
        gameId: Long,
    ) {
        boardItems.forEachIndexed { colIndex, imageView ->
            imageView.setOnClickListener {
                markPosition(
                    position =
                        Position(
                            horizontalCoordinate = BOARD_DISPLAY_SIZE - rowIndex,
                            verticalCoordinate = colIndex + 1,
                        ),
                    view = imageView,
                    gameId = gameId,
                )
            }
        }
    }

    private fun markPosition(
        position: Position,
        view: ImageView,
        gameId: Long,
    ) {
        if (!::gameState.isInitialized || gameState !is GameState.GameOver) {
            setGameState(position)
            if (gameState !is GameState.Error) {
                val currentColor = placementData.lastTurnColor ?: Color.BLACK
                setStoneImage(view)
                savePlacementInfo(gameId, position, currentColor)
                setCurrentTurnText()
            }
        }
    }

    private fun savePlacementInfo(
        gameId: Long,
        position: Position,
        color: Color,
    ) {
        placementDao.save(
            Placement(
                gameId = gameId,
                color = color.name,
                horizontalCoordinate = position.horizontalCoordinate,
                verticalCoordinate = position.verticalCoordinate,
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
        private const val BOARD_DISPLAY_SIZE = 15
    }
}
