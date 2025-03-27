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
import woowacourse.omok.domain.OmokAdapter
import woowacourse.omok.domain.OmokBoard
import woowacourse.omok.domain.OmokGame
import woowacourse.omok.domain.Position
import woowacourse.omok.domain.PutStoneResult
import woowacourse.omok.domain.PutStoneResult.Finished
import woowacourse.omok.domain.PutStoneResult.NextTurn
import woowacourse.omok.domain.Stone
import woowacourse.omok.domain.StoneState

class GameActivity : AppCompatActivity() {
    private lateinit var omokGame: OmokGame
    private lateinit var board: TableLayout
    private lateinit var dbHelper: DbHelper
    private lateinit var boardDao: BoardDao
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
        dbHelper = DbHelper(this)
        boardDao = BoardDao(dbHelper)

        initView()
        initGame()
    }

    private fun initView() {
        board = findViewById(R.id.board)
    }

    private fun initGame() {
        val storedStone = boardDao.queryStones(gameId)
        omokGame =
            if (storedStone.isNotEmpty()) {
                loadGame(storedStone)
            } else {
                OmokGame(OmokBoard(rule = OmokAdapter()))
            }
        updateTurnView(omokGame.turn)
        initBoard()
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
                    handlePutStoneResult(
                        view,
                        Stone(Position(x - 'A', y - 1), omokGame.turn),
                    )
                }
            }
    }

    private fun loadGame(storedStone: List<Stone>): OmokGame {
        val lastTurn = storedStone.last().state
        val omokBoard = OmokBoard(stones = storedStone, rule = OmokAdapter())
        storedStone.forEach { stone ->
            val row = board.getChildAt(14 - stone.position.y) as? TableRow
            val imageView = row?.getChildAt(stone.position.x) as? ImageView
            imageView?.let { view -> drawStone(view, stone.state) }
        }

        val turn = if (lastTurn == StoneState.BLACK) StoneState.WHITE else StoneState.BLACK
        updateTurnView(turn)
        return OmokGame(omokBoard, turn)
    }

    private fun drawStone(
        view: ImageView,
        state: StoneState,
    ) {
        if (state == StoneState.BLACK) {
            view.setImageResource(R.drawable.black_stone)
        } else {
            view.setImageResource(R.drawable.white_stone)
        }
    }

    private fun updateTurnView(turn: StoneState) {
        findViewById<TextView>(R.id.tv_nowTurn).text =
            getString(R.string.text_now_turn, turn)
    }

    private fun handlePutStoneResult(
        view: ImageView,
        stone: Stone,
    ) {
        when (omokGame.putStone(stone)) {
            is NextTurn -> {
                boardDao.insert(stone, gameId)
                drawStone(view, stone.state)
                omokGame.changeTurn()
                updateTurnView(omokGame.turn)
            }

            is Finished -> {
                boardDao.insert(stone, gameId)
                drawStone(view, omokGame.turn)

                // board 터치 막기
                board.children
                    .filterIsInstance<TableRow>()
                    .flatMap { it.children }
                    .filterIsInstance<ImageView>()
                    .forEach { it.setOnClickListener(null) }

                Toast
                    .makeText(
                        this,
                        getString(R.string.text_win_message, omokGame.turn.name),
                        Toast.LENGTH_LONG,
                    ).show()
            }

            is PutStoneResult.AlreadyPlaced -> {
                Toast.makeText(this, R.string.text_already_placed, Toast.LENGTH_SHORT).show()
            }

            is PutStoneResult.Violation -> {
                Toast.makeText(this, R.string.text_violate_rule, Toast.LENGTH_SHORT).show()
            }

            is PutStoneResult.InvalidPosition -> {
                Toast.makeText(this, R.string.text_invalid_position, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroy() {
        dbHelper.close()

        super.onDestroy()
    }
}
