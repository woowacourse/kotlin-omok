package woowacourse.omok

import android.os.Bundle
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import domain.state.State
import domain.state.end.End
import domain.state.running.BlackTurn
import domain.state.running.Running
import domain.state.running.WhiteTurn
import domain.stone.Board
import domain.stone.StonePosition
import view.OutputView

class MainActivity : AppCompatActivity() {

    val outputView: OutputView = OutputView()
    val board = findViewById<TableLayout>(R.id.board)
    val boardMap: Board = Board()
    var state: State = BlackTurn()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        outputView.printOmokStart()
        board
            .children
            .filterIsInstance<TableRow>()
            .forEachIndexed { row, it ->
                it.children.filterIsInstance<ImageView>()
                    .forEachIndexed { col, view ->
                        view.setOnClickListener { coordinateViewClickEvent(row, col, view) }
                    }
            }
    }

    private fun coordinateViewClickEvent(row: Int, col: Int, view: ImageView) {
        val stonePosition: StonePosition = StonePosition(col + 1, row + 1)
        if (!state.isEnd() && !(state as Running).isPlaced(boardMap, stonePosition)) {
            view.setImageResource(getStoneImage(state))
            state = state.next(boardMap, stonePosition)

            outputView.printBoard(boardMap.stones)
            outputView.printTurn(state, boardMap.stones)
        }
        if (state.isEnd()) {
            Toast.makeText(this, "${OutputView().getWinnerText(state as End)}돌이 승리하였습니다.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getStoneImage(state: State): Int {
        return when (state) {
            is BlackTurn -> R.drawable.stone_black_pokemon_ball_luxury
            is WhiteTurn -> R.drawable.stone_white_pokemon_ball_premier
            else -> 0
        }
    }
}
