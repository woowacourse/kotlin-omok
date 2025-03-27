package woowacourse.omok

import android.content.ContentValues
import android.database.Cursor
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
import woowacourse.omok.data.db.BoardContract
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

class MainActivity : AppCompatActivity() {
    private lateinit var omokGame: OmokGame
    private lateinit var board: TableLayout
    private lateinit var dbHelper: DbHelper
    private var gameId: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        gameId = intent.getLongExtra("game_id", -1)
        dbHelper = DbHelper(this)
        initView()
        initGame()
    }

    private fun initView() {
        board = findViewById(R.id.board)
    }

    private fun initGame() {
        val storedStone = getStoredStone(gameId)
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
                insertStone(stone, gameId)
                drawStone(view, stone.state)
                omokGame.changeTurn()
                updateTurnView(omokGame.turn)
            }

            is Finished -> {
                insertStone(stone, gameId)
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

    private fun getStoredStone(gameId: Long): List<Stone> {
        val dbReader = dbHelper.readableDatabase
        val result = mutableListOf<Stone>()

        val cursor: Cursor =
            dbReader.query(
                BoardContract.TABLE_NAME,
                arrayOf(
                    BoardContract.COLUMN_NAME_GAME_ID,
                    BoardContract.COLUMN_NAME_X,
                    BoardContract.COLUMN_NAME_Y,
                    BoardContract.COLUMN_NAME_STATE,
                ),
                "${BoardContract.COLUMN_NAME_GAME_ID} = ?",
                arrayOf(gameId.toString()),
                null,
                null,
                null,
            )

        with(cursor) {
            while (moveToNext()) {
                val x = getInt(getColumnIndexOrThrow(BoardContract.COLUMN_NAME_X))
                val y = getInt(getColumnIndexOrThrow(BoardContract.COLUMN_NAME_Y))
                val state = getString(getColumnIndexOrThrow(BoardContract.COLUMN_NAME_STATE))

                val stone = Stone(Position(x, y), StoneState.valueOf(state))
                result.add(stone)
            }
        }
        cursor.close()
        return result
    }

    private fun insertStone(
        stone: Stone,
        gameId: Long,
    ) {
        val db = dbHelper.writableDatabase

        val values =
            ContentValues().apply {
                put(BoardContract.COLUMN_NAME_GAME_ID, gameId)
                put(BoardContract.COLUMN_NAME_X, stone.position.x)
                put(BoardContract.COLUMN_NAME_Y, stone.position.y)
                put(BoardContract.COLUMN_NAME_STATE, stone.state.name)
            }

        db.insert(BoardContract.TABLE_NAME, null, values)
        db.close()
    }

    override fun onDestroy() {
        dbHelper.close()

        super.onDestroy()
    }
}
