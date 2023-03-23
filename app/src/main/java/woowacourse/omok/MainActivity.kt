package woowacourse.omok

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import omok.domain.Turn
import omok.domain.board.Board
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

        val db = BoardDBHelper(this).writableDatabase
        val boardUI = findViewById<TableLayout>(R.id.board)
        val board = Board()
        val turn = Turn(setOf(Black, White))
        val winningReferee = WinningReferee()

        boardUI
            .children
            .filterIsInstance<TableRow>()
            .flatMap { it.children }
            .filterIsInstance<ImageView>()
            .forEachIndexed { index, view ->
                view.setOnClickListener {
                    val selectedPosition = changeToPosition(index)
                    val isPlaced = place(board, selectedPosition, turn)

                    if (isPlaced) {
                        showSelectedStone(view, turn)
                        checkWinner(selectedPosition, board, winningReferee, turn.now)

                        val values = ContentValues()
                        values.put(BoardContract.TABLE_COLUMN_POSITION_INDEX, index)
                        values.put(
                            BoardContract.TABLE_COLUMN_STONE,
                            "${changeStoneToData(turn.now)}"
                        )
                        db.insert(BoardContract.TABLE_NAME, null, values)
                        turn.changeTurn()
                    }
                }
            }
    }

    private fun changeToPosition(index: Int): Position {
        val row = 14 - (index / 15)
        val column = index % 15
        return Position(Pair(column, row))
    }

    private fun changeStoneToData(stone: Stone): Int {
        return when (stone) {
            Black -> 0
            White -> 1
        }
    }

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

    private fun checkWinner(
        selectedPosition: Position,
        board: Board,
        winningReferee: WinningReferee,
        turn: Stone
    ) {
        if ((winningReferee.hasFiveOrMoreStoneInRow(board.positions, selectedPosition))) {
            val intent = Intent(this, ResultActivity::class.java)
            intent.putExtra("winner", turn.toPresentation())
            startActivity(intent)
            finish()
        }
    }
}
