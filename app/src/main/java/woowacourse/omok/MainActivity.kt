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
import woowacourse.omok.util.showToast

class MainActivity : AppCompatActivity() {
    private var gameState: GameState = BlackTurn(RenjuRule, Board(emptyMap()))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
    }

    private fun initView() {
        setContentView(R.layout.activity_main)
        val board = makeBoardView()
        initBoardView(board)
//        val boardView = OmokBoardView(makeBoardView(), gameState)
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
                placeOmokStone(index, view)
            }
        }
    }

    private fun placeOmokStone(
        index: Int,
        view: ImageView,
    ) {
        runCatching {
            onPlace(index, view)
        }.onFailure {
            showToast(this, "${it.message}")
        }
    }

    private fun onPlace(
        index: Int,
        view: ImageView,
    ) {
        val position = convertIndexToPosition(index)
        gameState = gameState.put(position)
        updateStoneImage(index, view)
    }

    private fun updateStoneImage(
        index: Int,
        view: ImageView,
    ) {
        gameState.board[convertIndexToPosition(index)]?.run {
            val targetColor = mapStoneColorToDrawable(this.color)
            view.setImageResource(targetColor)
        }
    }
}
