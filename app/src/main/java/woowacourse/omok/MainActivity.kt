package woowacourse.omok

import android.content.ContentValues
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import domain.OmokBoard
import domain.OmokGame
import domain.OmokGameListener
import domain.State
import domain.Stone

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val board = findViewById<TableLayout>(R.id.board)

        val omokBoard = board
            .children
            .filterIsInstance<TableRow>()
            .flatMap { it.children }
            .filterIsInstance<ImageView>()
            .toList()

        val omokGameListener = object : OmokGameListener {

            override fun onMove(omokBoard: OmokBoard, state: State, stone: Stone) {}

            override fun onMoveFail() {
                Toast.makeText(this@MainActivity, "중복된 위치입니다.", Toast.LENGTH_SHORT).show()
            }

            override fun onForbidden() {
                Toast.makeText(this@MainActivity, "금수 입니다.", Toast.LENGTH_SHORT).show()
            }

            override fun onFinish(state: State) {
                // Toast.makeText(this@MainActivity, "${state.name} 승", Toast.LENGTH_SHORT).show()
            }
        }

        val omokGame = OmokGame(omokGameListener = omokGameListener)

        val db = OmokDbHelper(this).writableDatabase

        var isBlackTurn = loadGame(db, omokBoard, omokGame)

        omokBoard.forEachIndexed { index, view ->
            val row = index / BOARD_SIZE
            val col = index % BOARD_SIZE
            view.setOnClickListener {

                val state = if (isBlackTurn) {
                    State.BLACK
                } else {
                    State.WHITE
                }

                val stoneImage = if (isBlackTurn) {
                    R.drawable.black_stone
                } else {
                    R.drawable.white_stone
                }

                if (omokGame.successTurn(Stone(row, col), state)) {
                    view.setImageResource(stoneImage)
                    saveStone(db, index, state)
                    isBlackTurn = !isBlackTurn
                    if (omokGame.isVictory(state)) {
                        val intent = Intent(this, GameOverActivity::class.java).apply {
                            putExtra(OMOK_WINNER, state.name)
                        }
                        startActivity(intent)
                    }
                }
            }
        }
    }

    private fun saveStone(db: SQLiteDatabase, index: Int, state: State) {
        val values = ContentValues()
        values.put(OmokContract.TABLE_COLUMN_INDEX, index)
        values.put(OmokContract.TABLE_COLUMN_COLOR, state.name)

        db.insert(OmokContract.TABLE_NAME, null, values)
    }

    private fun loadGame(
        db: SQLiteDatabase,
        omokBoard: List<ImageView>,
        omokGame: OmokGame
    ): Boolean {

        var blackCount = 0
        var whiteCount = 0
        val cursor = getStonesCursor(db)

        while (cursor.moveToNext()) {
            val index = cursor.getInt(
                cursor.getColumnIndexOrThrow(OmokContract.TABLE_COLUMN_INDEX)
            )
            val color = cursor.getString(
                cursor.getColumnIndexOrThrow(OmokContract.TABLE_COLUMN_COLOR)
            )
            val row = index / BOARD_SIZE
            val column = index % BOARD_SIZE

            if (color == State.BLACK.name) {
                omokBoard[index].setImageResource(R.drawable.black_stone)
                omokGame.successTurn(Stone(row, column), State.BLACK)
                blackCount++
            } else {
                omokBoard[index].setImageResource(R.drawable.white_stone)
                omokGame.successTurn(Stone(row, column), State.WHITE)
                whiteCount++
            }
        }
        cursor.close()

        if (blackCount != whiteCount) {
            return false
        }
        return true
    }

    private fun getStonesCursor(db: SQLiteDatabase): Cursor {
        return db.query(
            OmokContract.TABLE_NAME,
            arrayOf(
                OmokContract.TABLE_COLUMN_INDEX,
                OmokContract.TABLE_COLUMN_COLOR
            ),
            null,
            null,
            null,
            null,
            null
        )
    }

    companion object {
        private const val BOARD_SIZE = 15
        const val OMOK_WINNER = "OMOK_WINNER"
    }
}
