package woowacourse.omok

import android.database.sqlite.SQLiteDatabase
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
import woowacourse.omok.model.database.PlacementContract
import woowacourse.omok.model.database.PlacementDbHelper
import woowacourse.omok.model.state.GameState

class MainActivity : AppCompatActivity() {
    private val placementData: Board by lazy { Board() }
    private lateinit var gameState: GameState
    private lateinit var db: SQLiteDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val dbHelper = PlacementDbHelper(this)
        db = dbHelper.writableDatabase

        val gameId = intent.getLongExtra(GAME_ID, 0)
        val gameTitle = intent.getStringExtra(GAME_TITLE) ?: finish()
        findViewById<TableLayout>(R.id.board).apply {
            children
                .filterIsInstance<TableRow>()
                .flatMap { it.children }
                .filterIsInstance<ImageView>()
                .forEachIndexed { index, view ->
                    view.setOnClickListener {
                        markPosition(index, view, gameId)
                    }
                }
        }
    }

    private fun markPosition(
        index: Int,
        view: ImageView,
        gameId: Long,
    ) {
        val position = getInputPosition(index)
        if (!::gameState.isInitialized || gameState !is GameState.GameOver) {
            gameState = playEachTurn(position)
            showGameStateMessage(gameState)
            if (gameState !is GameState.Error) {
                setStoneImage(view)
                db.execSQL(
                    "INSERT INTO ${PlacementContract.TABLE_NAME} (" +
                        "${PlacementContract.COLUMN_ROOM_ID}," +
                        " ${PlacementContract.COLUMN_COLOR}," +
                        " ${PlacementContract.COLUMN_VERTICAL_COORDINATE}," +
                        " ${PlacementContract.COLUMN_HORIZONTAL_COORDINATE}" +
                        ") \n" +
                        "VALUES ($gameId, '${placementData.lastPlacement?.color?.name}', ${position.second}, ${position.first})",
                )
            }
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
