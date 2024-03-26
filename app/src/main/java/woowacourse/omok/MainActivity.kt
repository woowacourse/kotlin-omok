package woowacourse.omok

import android.os.Bundle
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import omok.model.Board
import omok.model.rule.RenjuRule
import omok.model.state.BlackTurn
import omok.model.state.GameState

class MainActivity : AppCompatActivity() {
    private var state: GameState = BlackTurn(RenjuRule, Board(emptyMap()))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
    }

    private fun initView() {
        setContentView(R.layout.activity_main)
        val board = makeBoardView()
        initBoardView(board)
    }

    private fun makeBoardView() =
        findViewById<TableLayout>(R.id.board)
            .children
            .filterIsInstance<TableRow>()
            .flatMap { it.children }
            .filterIsInstance<ImageView>()

    private fun initBoardView(boardView: Sequence<ImageView>) {
        boardView.forEachIndexed { index, view ->
            view.setOnClickListener {
                onPlace(index)
            }
        }
    }

    private fun onPlace(index: Int) {
        val position = indexToPosition(index)
        state = state.put(position)
    }
}
