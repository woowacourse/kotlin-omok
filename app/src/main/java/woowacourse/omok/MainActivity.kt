package woowacourse.omok

import android.content.DialogInterface
import android.os.Bundle
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import androidx.core.view.forEachIndexed
import domain.state.BlackTurn
import domain.state.End
import domain.state.State
import domain.state.WhiteTurn
import domain.stone.Board
import domain.stone.Stone
import domain.stone.StonePosition
import domain.stone.StoneType

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val omokBoard = findViewById<TableLayout>(R.id.board)
            .children
            .filterIsInstance<TableRow>()
            .flatMap { it.children }
            .filterIsInstance<ImageView>()

        startGame(omokBoard, BlackTurn(Board()))
    }

    private fun startGame(board: Sequence<ImageView>, state: State) {
        when (state) {
            is BlackTurn -> {
                board.forEachIndexed { index, view ->
                    view.setOnClickListener {
                        val currentState = state.put(
                            Stone(
                                StonePosition.from((index % 15) + 1, (index / 15) + 1),
                                StoneType.BLACK,
                            ),
                        )
                        if (currentState !is BlackTurn) {
                            view.setImageResource(R.drawable.black_stone)
                        } else {
                            Toast.makeText(this, "금수입니다.", Toast.LENGTH_LONG).show()
                        }
                        startGame(board, currentState)
                    }
                }
            }
            is WhiteTurn -> {
                board.forEachIndexed { index, view ->
                    view.setOnClickListener {
                        val currentState = state.put(
                            Stone(
                                StonePosition.from((index % 15) + 1, (index / 15) + 1),
                                StoneType.WHITE,
                            ),
                        )
                        if (currentState !is WhiteTurn) {
                            view.setImageResource(R.drawable.white_stone)
                        } else {
                            Toast.makeText(this, "금수입니다.", Toast.LENGTH_LONG).show()
                        }
                        startGame(board, currentState)
                    }
                }
            }

            is End -> AlertDialog.Builder(this)
                .setTitle("결과")
                .setMessage("${state.getWinner()}이 우승했습니다. 게임을 다시 시작합니다.")
                .setPositiveButton(
                    "ok",
                    object : DialogInterface.OnClickListener {
                        override fun onClick(p0: DialogInterface?, p1: Int) {
                            board.forEach { it.setImageResource(0) }
                            startGame(board, BlackTurn(Board()))
                        }
                    },
                )
                .create()
                .show()
        }
    }
}
