package woowacourse.omok.presentation

import android.os.Bundle
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.children
import woowacourse.omok.R
import woowacourse.omok.data.db.BoardDao
import woowacourse.omok.data.db.DbHelper
import woowacourse.omok.data.db.GameDao
import woowacourse.omok.domain.OmokGame
import woowacourse.omok.domain.Position
import woowacourse.omok.domain.PutStoneResult
import woowacourse.omok.domain.PutStoneResult.Finished
import woowacourse.omok.domain.PutStoneResult.Success
import woowacourse.omok.domain.Stone
import woowacourse.omok.domain.StoneState

class GameActivity : AppCompatActivity() {
    private lateinit var omokGame: OmokGame
    private lateinit var board: TableLayout
    private val dbHelper: DbHelper by lazy { DbHelper(this) }
    private val boardDao: BoardDao by lazy { BoardDao(dbHelper) }
    private val gameDao: GameDao by lazy { GameDao(dbHelper, boardDao) }
    private var gameId: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_game)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        gameId = intent.getLongExtra("game_id", -1)
        board = findViewById(R.id.board)
        initBoard()
        initGame()
    }

    private fun initGame() {
        omokGame = gameDao.getOrCreateGame(gameId)
        val storedStones = gameDao.getStoredStones(gameId)
        if (storedStones.isNotEmpty()) {
            loadGame(storedStones)
        }
    }

    private fun initBoard() {
        val columns = ('A'..'O').toList()
        val rows = (15 downTo 1).toList()

        board
            .children
            .filterIsInstance<TableRow>()
            .flatMap { it.children }
            .filterIsInstance<ImageView>()
            .forEachIndexed { index, view ->
                val x = columns[index % 15]
                val y = rows[index / 15]
                view.tag = "$x$y"

                view.setOnClickListener {
                    onStonePlaced(
                        view,
                        Stone(Position(x.toBoardIndex, y.toBoardIndex), omokGame.turn),
                    )
                }
            }
    }

    private fun loadGame(storedStone: List<Stone>) {
        val lastTurn = storedStone.last().state
        initBoard()

        board.children
            .filterIsInstance<TableRow>()
            .flatMap { it.children }
            .filterIsInstance<ImageView>()
            .forEach { view ->
                val tag = view.tag as String
                val position = tag.toBoardPosition()
                val state = omokGame.getState(position)
                drawStone(view, state)
            }

        val turn = if (lastTurn == StoneState.BLACK) StoneState.WHITE else StoneState.BLACK
        updateTurnView(turn)
    }

    private fun drawStone(
        view: ImageView,
        state: StoneState,
    ) {
        when (state) {
            StoneState.BLACK -> view.setImageResource(R.drawable.black_stone)
            StoneState.WHITE -> view.setImageResource(R.drawable.white_stone)
            else -> view.setImageResource(0)
        }
    }

    private fun updateTurnView(turn: StoneState) {
        findViewById<TextView>(R.id.tv_nowTurn).text =
            getString(R.string.text_now_turn, turn)
    }

    private fun onStonePlaced(
        view: ImageView,
        stone: Stone,
    ) {
        when (omokGame.putStone(stone)) {
            is Success -> handleSuccess(stone, view)
            is Finished -> handleGameFinished(stone, view)
            is PutStoneResult.AlreadyPlaced -> handleAlreadyPlaced()
            is PutStoneResult.Violation -> handleRuleViolation()
            is PutStoneResult.InvalidPosition -> handleInvalidPosition()
        }
    }

    private fun handleSuccess(
        stone: Stone,
        view: ImageView,
    ) {
        boardDao.insert(stone, gameId)
        drawStone(view, stone.state)
        omokGame.changeTurn()
        updateTurnView(omokGame.turn)
    }

    private fun handleGameFinished(
        stone: Stone,
        view: ImageView,
    ) {
        boardDao.insert(stone, gameId)
        drawStone(view, omokGame.turn)
        disableBoardTouch()
        showWinningToast(getString(R.string.text_win_message, omokGame.turn.name))
    }

    private fun handleAlreadyPlaced() {
        showToast(R.string.text_already_placed)
    }

    private fun handleRuleViolation() {
        showToast(R.string.text_violate_rule)
    }

    private fun handleInvalidPosition() {
        showToast(R.string.text_invalid_position)
    }

    private fun showToast(
        messageResId: Int,
        duration: Int = Toast.LENGTH_SHORT,
    ) {
        Toast.makeText(this, getString(messageResId), duration).show()
    }

    private fun showWinningToast(
        message: String,
        duration: Int = Toast.LENGTH_LONG,
    ) {
        Toast.makeText(this, message, duration).show()
    }

    // board 터치 막기
    private fun disableBoardTouch() {
        board.children
            .filterIsInstance<TableRow>()
            .flatMap { it.children }
            .filterIsInstance<ImageView>()
            .forEach { it.setOnClickListener(null) }
    }

    private fun String.toBoardPosition(): Position {
        val x = this[0].toBoardIndex
        val y = this.substring(1).toInt().toBoardIndex

        return Position(x, y)
    }

    private val Char.toBoardIndex: Int
        get() = this - 'A'

    private val Int.toBoardIndex: Int
        get() = 15 - this

    override fun onDestroy() {
        dbHelper.close()

        super.onDestroy()
    }
}
