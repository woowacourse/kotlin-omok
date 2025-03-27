package woowacourse.omok

import android.content.ContentValues
import android.database.Cursor
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.children
import woowacourse.omok.database.OmokContract
import woowacourse.omok.database.OmokDbHelper
import woowacourse.omok.model.Board
import woowacourse.omok.model.Color
import woowacourse.omok.model.Game
import woowacourse.omok.model.MoveResult
import woowacourse.omok.model.Stone
import woowacourse.omok.model.position.Col
import woowacourse.omok.model.position.Position
import woowacourse.omok.model.position.Row
import woowacourse.omok.model.rule.RenjuRule
import woowacourse.omok.view.OutputViewAndroid

class MainActivity : AppCompatActivity() {
    private val game = Game(Board(), RenjuRule())
    private val outputView = OutputViewAndroid()
    private val dbHelper = OmokDbHelper(this)

    private fun restoreGame(boardLayout: TableLayout) {
        val stones: List<Stone> = queryAll()
        stones.forEach { stone ->
            game.processTurn(stone.position, stone.color)
            val stoneImage =
                when (stone.color) {
                    Color.BLACK -> R.drawable.black_stone
                    Color.WHITE -> R.drawable.white_stone
                }
            boardLayout.filterImageViews().forEachIndexed { index, view ->
                if (index % 15 == stone.position.x.value - 1 && index / 15 == stone.position.y.value - 1) {
                    view.setImageResource(stoneImage)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialize()
        val board: TableLayout = findViewById(R.id.board)
        restoreGame(board)

        outputView.printOmokStart(board)
        setListeners(board)
    }

    override fun onDestroy() {
        dbHelper.close()
        super.onDestroy()
    }

    private fun initialize() {
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun insertData(
        x: Col,
        y: Row,
        color: Color,
    ) {
        val db = dbHelper.writableDatabase
        val values =
            ContentValues().apply {
                put(OmokContract.COLUMN_NAME_X, x.value)
                put(OmokContract.COLUMN_NAME_Y, y.value)
                put(OmokContract.COLUMN_NAME_COLOR, color.name)
            }
        db.insert(OmokContract.TABLE_NAME, null, values)
        queryAll().forEach {
            Log.i(
                "SQL",
                "${it.position.x.value} ${it.position.y.value} ${it.color.name}",
            )
        }
        Log.i("SQL", "----------------------")
    }

    private fun queryAll(): List<Stone> {
        val dbReader = dbHelper.readableDatabase
        val result = mutableListOf<Stone>()

        val cursor: Cursor = dbReader.rawQuery("SELECT * FROM ${OmokContract.TABLE_NAME}", null)

        with(cursor) {
            while (moveToNext()) {
                val x: Int = getInt(getColumnIndexOrThrow(OmokContract.COLUMN_NAME_X))
                val y: Int = getInt(getColumnIndexOrThrow(OmokContract.COLUMN_NAME_Y))
                val color: Color =
                    when (getString(getColumnIndexOrThrow(OmokContract.COLUMN_NAME_COLOR))) {
                        "BLACK" -> Color.BLACK
                        "WHITE" -> Color.WHITE
                        else -> throw IllegalStateException()
                    }
                result.add(Stone(Position(Col(x), Row(y)), color))
            }
        }
        cursor.close()
        return result
    }

    private fun setListeners(board: TableLayout) {
        board.filterImageViews().forEachIndexed { index, view ->
            view.setOnClickListener { onClick(index, view, board) }
        }
    }

    private fun onClick(
        index: Int,
        view: ImageView,
        boardLayout: TableLayout,
    ) {
        val color: Color = game.chooseTurn()
        val stoneImage =
            when (color) {
                Color.BLACK -> R.drawable.black_stone
                Color.WHITE -> R.drawable.white_stone
            }

        val x = Col(index % game.board.col.value + 1)
        val y = Row(index / game.board.row.value + 1)
        when (val moveResult: MoveResult = game.processTurn(Position(x, y), color)) {
            is MoveResult.Success.Playing -> {
                view.setImageResource(stoneImage)
                insertData(x, y, color)
            }

            is MoveResult.Success.Finished -> {
                view.setImageResource(stoneImage)
                insertData(x, y, color)
                outputView.printMoveResult(moveResult, this, boardLayout)
                boardLayout.filterImageViews().forEach { it.setOnClickListener(null) }
                dbHelper.writableDatabase.delete(OmokContract.TABLE_NAME, null, null)
                return
            }

            is MoveResult.Failure -> {
                outputView.printMoveResult(moveResult, this, boardLayout)
            }
        }
    }

    private fun TableLayout.filterImageViews(): Sequence<ImageView> {
        return children.filterIsInstance<TableRow>().flatMap { it.children }
            .filterIsInstance<ImageView>()
    }
}
