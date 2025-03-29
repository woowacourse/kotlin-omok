package woowacourse.omok

import android.content.ContentValues
import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.children
import omok.domain.Board
import omok.domain.FiveRule
import omok.domain.Position
import omok.domain.RenjuRuleAdapter
import omok.domain.Stone
import omok.domain.StoneType
import omok.domain.Turn

class MainActivity : AppCompatActivity() {
    private val omokBoard: Board = Board(RenjuRuleAdapter())
    private val turn = Turn()
    private val fiveRule = FiveRule()

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
        val board = findViewById<TableLayout>(R.id.board)
        board
            .children
            .filterIsInstance<TableRow>()
            .flatMap { it.children }
            .filterIsInstance<ImageView>()
            .forEachIndexed { index, view ->
                val row = index / Board.BOARD_SIZE
                val column = index % Board.BOARD_SIZE

                val stone = stones.find { it.position.row == row && it.position.column == column }
                if (stone != null) {
                    putStone(view, row, column, stone.color)
                }

                view.setOnClickListener {
                    putStone(view, row, column, turn.color)
                }
            }
        showTurnColorToast()
    }

    override fun onDestroy() {
        dbHelper.close()
        super.onDestroy()
    }


    private fun putStone(
        view: ImageView,
        row: Int,
        column: Int,
        color: StoneType,
    ) {
        if (omokBoard.isFull()) {
            Toast.makeText(this, "더 이상 돌을 놓을 수 없어 무승부입니다.", Toast.LENGTH_SHORT).show()
        }
        if (omokBoard.isInvalidPosition(Position(row, column))) {
            Toast.makeText(this, "이미 돌을 놓은 자리입니다.", Toast.LENGTH_SHORT).show()
        }
        if (omokBoard.isInvalidBlackPosition(Stone(Position(row, column), turn.color))) {
            Toast.makeText(this, "흑돌이 놓을 수 없는 금수입니다.", Toast.LENGTH_SHORT).show()
        }

        if (!omokBoard.isFull() && !omokBoard.isInvalidPosition(Position(row, column)) && !omokBoard.isInvalidBlackPosition(Stone(Position(row, column), turn.color))) {
            omokBoard.put(Position(row, column), turn.color)
            val stoneColor = if (color == StoneType.WHITE) "white" else "black"
            insertStone(row, column, stoneColor)
            if (color == StoneType.WHITE) {
                view.setImageResource(R.drawable.white_stone)
            } else {
                view.setImageResource(R.drawable.black_stone)
            }
            if (checkOmok()) {
                showWinner()
            } else {
                turn.next()
                showTurnColorToast()
            }
        }
    }
    private fun insertStone(row: Int, column: Int, color: String) {
        val db = dbHelper.writableDatabase

        val values = ContentValues().apply {
            put(BoardContract.COLUMN_NAME_COLOR, color)
            put(BoardContract.COLUMN_NAME_POSITION_ROW, row)
            put(BoardContract.COLUMN_NAME_POSITION_COLUMN, column)
        }

        val newRowId = db.insert(BoardContract.TABLE_NAME_BOARD, null, values)
        if (newRowId == -1L) {
            Log.e("MainActivity", "insert failed")
        } else {
            Log.d("MainActivity", "insert success: $newRowId")
        }
        db.close()
    }

    private fun queryStones(): List<Stone> {
        val dbReader = dbHelper.readableDatabase
        val result = mutableListOf<Stone>()

        val cursorCheck = dbReader.rawQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='board'", null)
        if (cursorCheck.count == 0) {
            cursorCheck.close()
            return emptyList()
        }
        cursorCheck.close()

        val cursor: Cursor = dbReader.rawQuery("SELECT * FROM board", null)

        with(cursor) {
            while (moveToNext()) {
                val color = getString(getColumnIndexOrThrow(BoardContract.COLUMN_NAME_COLOR))
                val row = getInt(getColumnIndexOrThrow(BoardContract.COLUMN_NAME_POSITION_ROW))
                val column = getInt(getColumnIndexOrThrow(BoardContract.COLUMN_NAME_POSITION_COLUMN))
                val stoneColor = if (color == "black") StoneType.BLACK else StoneType.WHITE
                result.add(Stone(Position(row, column), stoneColor))
            }
        }
        cursor.close()
        return result
    }

    private fun showTurnColorToast() {
         val turnColor = if (turn.isWhite()) "백" else "흑"
        Toast.makeText(this, "${turnColor}의 차례입니다.", Toast.LENGTH_SHORT).show()
    }

    private fun showWinner() {
        val turnColor = if (turn.isWhite()) "백" else "흑"

        val alertDialog = AlertDialog.Builder(this).run {
            setMessage("${turnColor}의 승리입니다!")
        }

        alertDialog.setOnDismissListener {
            restart()
        }
        alertDialog.show()
    }

    private fun restart() {
        val db = dbHelper.writableDatabase
        db.execSQL(BoardContract.SQL_DELETE_BOARD_ENTRIES)
        db.execSQL(BoardContract.SQL_CREATE_BOARD_ENTERIES)
        db.close()
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun checkOmok(): Boolean {
        val lastStone = omokBoard.stones.lastStone()
        if (lastStone != null) {
            return fiveRule.isOmok(lastStone, omokBoard.stones)
        }
        return false
    }
}
