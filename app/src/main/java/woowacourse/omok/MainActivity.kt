package woowacourse.omok

import android.content.ContentValues
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import omok.domain.Turn
import omok.domain.board.Board
import omok.domain.board.Column
import omok.domain.board.Position
import omok.domain.judgment.WinningReferee
import omok.domain.player.Black
import omok.domain.player.Stone
import omok.domain.player.White
import omok.view.model.toPresentation

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val boardView = findViewById<TableLayout>(R.id.board)
            .children
            .filterIsInstance<TableRow>()
            .flatMap { it.children }
            .filterIsInstance<ImageView>()
        val db = BoardDBHelper(this).writableDatabase
        val boardData = getDB(db)
        val board = boardData.first
        val turn = boardData.second
        val winningReferee = WinningReferee()
        insertStoneToView(boardView, board)

        boardView.forEachIndexed { index, view ->
            view.setOnClickListener {
                val selectedPosition = changeIndexToPosition(index)
                val isPlaced = place(board, selectedPosition, turn)

                if (isPlaced) {
                    showSelectedStone(view, turn)
                    if ((winningReferee.hasFiveOrMoreStoneInRow(board.positions, selectedPosition))) goToResultView(turn.now)
                    insertData(index, turn, db)
                    turn.nextTurn()
                }
            }
        }
    }

    private fun insertData(
        index: Int,
        turn: Turn,
        db: SQLiteDatabase
    ) {
        val values = ContentValues().apply {
            put(BoardContract.TABLE_COLUMN_POSITION_INDEX, index)
            put(BoardContract.TABLE_COLUMN_STONE, "${changeStoneToData(turn.now)}")
        }
        db.insert(BoardContract.TABLE_NAME, null, values)
    }

    private fun changeIndexToPosition(index: Int): Position {
        val row = 14 - (index / 15)
        val column = index % 15
        return Position(Pair(column, row))
    }

    private fun changePositionToIndex(position: Position): Int {
        var result = 0
        Column.values().forEachIndexed { index, column ->
            if (column == position.column) result += index
        }
        result += (14 - position.row.axis) * 15
        return result
    }

    private fun changeStoneToData(stone: Stone) = if (stone == Black) 0 else 1

    private fun changeDataToStone(stone: Int) = if (stone == 0) Black else White

    private fun place(
        board: Board,
        selectedPosition: Position,
        turn: Turn
    ): Boolean {
        if (board.positions[selectedPosition] != null) {
            Toast.makeText(this, "빈 칸이 아닙니다", Toast.LENGTH_SHORT).show()
            return false
        }

        runCatching {
            board.place(selectedPosition, turn.now)
        }.onFailure {
            Toast.makeText(this, "${it.message}", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun showSelectedStone(cell: ImageView, turn: Turn) {
        if (turn.now == Black)
            cell.setImageResource(R.drawable.black_stone)
        else
            cell.setImageResource(R.drawable.white_stone)
    }

    private fun goToResultView(
        turn: Stone
    ) {
        val intent = Intent(this, ResultActivity::class.java)
        intent.putExtra("winner", turn.toPresentation())
        startActivity(intent)
        finish()
    }

    private fun getDB(db: SQLiteDatabase): Pair<Board, Turn> {
        val cells: MutableMap<Position, Stone?> = Board.POSITIONS.associateWith { null }.toMutableMap()

        val cursor = db.rawQuery("SELECT * FROM ${BoardContract.TABLE_NAME}", null)
        val count = cursor.count
        if (cursor.moveToFirst()) {
            do {
                val index = cursor.getInt(cursor.getColumnIndexOrThrow(BoardContract.TABLE_COLUMN_POSITION_INDEX))
                val stone = cursor.getInt(cursor.getColumnIndexOrThrow(BoardContract.TABLE_COLUMN_STONE))

                cells[changeIndexToPosition(index)] = changeDataToStone(stone)
            } while (cursor.moveToNext())
        }
        cursor.close()
        if (count != 0 && count % 2 == 1)
            return Pair(Board(cells), Turn(setOf(White, Black)))
        return Pair(Board(cells), Turn(setOf(Black, White)))
    }

    private fun insertStoneToView(boardView: Sequence<ImageView>, board: Board) {
        board.positions.forEach { (position, stone) ->
            if (stone != null) {
                boardView.toList()[changePositionToIndex(position)].setImageResource(changeStoneToImg(stone))
            }
        }
    }

    private fun changeStoneToImg(stone: Stone) =
        if (stone == Black) R.drawable.black_stone else R.drawable.white_stone

    override fun onDestroy() {
        super.onDestroy()
        val db = BoardDBHelper(this).writableDatabase
        db.execSQL("DELETE FROM ${BoardContract.TABLE_NAME}")
        db.close()
    }
}
