package woowacourse.omok

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import omok.controller.Controller
import omok.domain.Turn
import omok.domain.board.Board
import omok.domain.board.Position
import omok.domain.judgment.WinningReferee
import omok.domain.player.Black
import omok.domain.player.White
import omok.view.InputView
import omok.view.OutputView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val boardUI = findViewById<TableLayout>(R.id.board)

        val board = Board()
        var turn = Turn(setOf(Black, White))
        val winningReferee = WinningReferee()
        boardUI.children.filterIsInstance<TableRow>().forEachIndexed { index, t ->
            t.children.filterIsInstance<ImageView>().forEachIndexed { index2, t2 ->
                t2.setOnClickListener {
                    val selectedPosition = Position(Pair(index2, 14 - index))
                    board.place(selectedPosition, turn.now)
                    Log.i("positionTest", selectedPosition.toString())
                    if (turn.now == Black)
                        t2.setImageResource(R.drawable.black_stone)
                    else
                        t2.setImageResource(R.drawable.white_stone)

                    turn.changeTurn()
                }
            }
        }
    }

}
