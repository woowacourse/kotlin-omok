package woowacourse.omok

import android.content.ContentValues
import android.database.Cursor
import android.os.Bundle
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.children
import woowacourse.omok.data.DbHelper
import woowacourse.omok.data.OmokContract
import woowacourse.omok.domain.OmokBoard
import woowacourse.omok.domain.OmokGame
import woowacourse.omok.domain.Point
import woowacourse.omok.domain.state.Finished
import woowacourse.omok.domain.state.Playing
import woowacourse.omok.domain.stone.OmokStones
import woowacourse.omok.domain.stone.Stone
import woowacourse.omok.domain.stone.StoneColor

class MainActivity : AppCompatActivity() {
    private lateinit var dbHelper: DbHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        dbHelper = DbHelper(this)
        val stones = queryStones()
        val omokBoard = OmokBoard(stones = stones)
        val omokGame = OmokGame(omokBoard)

        val board = findViewById<TableLayout>(R.id.board)
        board
            .children
            .filterIsInstance<TableRow>()
            .toList()
            .reversed()
            .forEachIndexed { rowIndex, row ->
                row.children
                    .filterIsInstance<ImageView>()
                    .forEachIndexed { colIndex, view ->
                        view.tag = Point(rowIndex, colIndex)
                        view.setOnClickListener {
                            if (omokGame.state is Playing) {
                                playGame(omokGame, view)
                            }
                        }
                    }
            }
        stones.stones.forEach { stone ->
            val view = board.findViewWithTag<ImageView>(stone.point)
            when (stone.color) {
                StoneColor.BLACK -> view.setImageResource(R.drawable.black_stone)
                StoneColor.WHITE -> view.setImageResource(R.drawable.white_stone)
            }
        }
    }

    private fun playGame(
        omokGame: OmokGame,
        view: ImageView,
    ) {
        omokGame.play(
            onTurn = { _, _ -> },
            onPointSelected = { view.tag as Point },
            onForbiddenMove = { message ->
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            },
            onStonePlaced = { _, stone ->
                when (stone.color) {
                    StoneColor.BLACK -> view.setImageResource(R.drawable.black_stone)
                    StoneColor.WHITE -> view.setImageResource(R.drawable.white_stone)
                }
                insertStone(stone)
            },
        )
        if (omokGame.state is Finished) {
            omokGame.finish { Toast.makeText(this, "$it", Toast.LENGTH_SHORT).show() }
            deleteStones()
        }
    }

    private fun insertStone(stone: Stone) {
        val db = dbHelper.writableDatabase
        val values =
            ContentValues().apply {
                put(OmokContract.COLUMN_NAME_COLOR, stone.color.name)
                put(OmokContract.COLUMN_NAME_ROW, stone.point.row)
                put(OmokContract.COLUMN_NAME_COLUMN, stone.point.col)
            }
        db.insert(OmokContract.TABLE_NAME, null, values)
        db.close()
    }

    private fun queryStones(): OmokStones {
        val dbReader = dbHelper.readableDatabase
        val result = mutableSetOf<Stone>()

        val cursor: Cursor =
            dbReader.rawQuery("SELECT * FROM ${OmokContract.TABLE_NAME}", arrayOf())
        with(cursor) {
            while (moveToNext()) {
                val color =
                    StoneColor.valueOf(getString(getColumnIndexOrThrow(OmokContract.COLUMN_NAME_COLOR)))
                val row = getInt(getColumnIndexOrThrow(OmokContract.COLUMN_NAME_ROW))
                val col = getInt(getColumnIndexOrThrow(OmokContract.COLUMN_NAME_COLUMN))
                result.add(Stone(color, Point(row, col)))
            }
        }
        cursor.close()
        return OmokStones(result)
    }

    private fun deleteStones() {
        val db = dbHelper.writableDatabase
        db.delete(OmokContract.TABLE_NAME, null, null)
        db.close()
    }
}
