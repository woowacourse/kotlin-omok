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

        boardUI.children.filterIsInstance<TableRow>().forEachIndexed { row, t ->
            t.children.filterIsInstance<ImageView>().forEachIndexed { column, t2 ->
                t2.setOnClickListener {
                    val selectedPosition = Position(Pair(column, 14 - row))
                    val check = place(board, selectedPosition, turn)
                    if (check) {
                        if (turn.now == Black)
                            t2.setImageResource(R.drawable.black_stone)
                        else
                            t2.setImageResource(R.drawable.white_stone)

                        checkWinner(selectedPosition, board, winningReferee, turn.now)
                        turn.changeTurn()
                    }
                }
            }
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
