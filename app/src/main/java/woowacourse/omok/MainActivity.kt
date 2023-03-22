package woowacourse.omok

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

        val boardUI = findViewById<TableLayout>(R.id.board)
        val board = Board()
        val turn = Turn(setOf(Black, White))
        val winningReferee = WinningReferee()

        boardUI
            .children
            .filterIsInstance<TableRow>()
            .flatMap { it.children }
            .filterIsInstance<ImageView>().toList()
            .forEachIndexed { index, view ->
                view.setOnClickListener {
                    val selectedPosition = changeToPosition(index)
                    place(board, selectedPosition, turn, winningReferee)

                    insertStoneToView(boardUI, board)
                }
            }
    }

    private fun changeToPosition(index: Int): Position {
        val row = 14 - (index / 15)
        val column = index % 15
        return Position(Pair(column, row))
    }

    private fun changeToIndex(position: Position): Int {
        var result = 0
        Column.values().forEachIndexed { index, column ->
            if (column == position.column) result += index
        }
        result += (14 - position.row.axis) * 15
        return result
    }

    private fun insertStoneToView(boardUI: TableLayout, board: Board) {
        val stoneImages = mapOf(Black to R.drawable.black_stone, White to R.drawable.white_stone)
        board.positions.forEach { (position, stone) ->
            if (stone != null) {
                boardUI
                    .children
                    .filterIsInstance<TableRow>()
                    .flatMap { it.children }
                    .filterIsInstance<ImageView>()
                    .toList()[changeToIndex(position)].setImageResource(stoneImages[stone]!!)
            }
            // 비효율적!! 수정 필요
        }
    }

    private fun place(
        board: Board,
        selectedPosition: Position,
        turn: Turn,
        winningReferee: WinningReferee
    ) {
        if (board.positions[selectedPosition] != null) {
            Toast.makeText(this, "빈 칸이 아닙니다", Toast.LENGTH_SHORT).show()
        }

        runCatching {
            board.place(selectedPosition, turn.now)
        }.onFailure {
            Toast.makeText(this, "${it.message}", Toast.LENGTH_SHORT).show()
        }.onSuccess {
            checkWinner(selectedPosition, board, winningReferee, turn.now)
            turn.changeTurn()
        }
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
        }
    }
}
