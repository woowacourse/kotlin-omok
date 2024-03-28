package woowacourse.omok

import android.os.Bundle
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import omok.model.Board
import omok.model.rule.RenjuRule
import omok.model.state.BlackTurn
import omok.model.state.GameState

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

    private fun placeOmokStone(index: Int, view: ImageView) {
        if (gameState is GameState.Finish) {
            Toast.makeText(this, "${gameState.winner?.color}의 승리입니다.", Toast.LENGTH_SHORT)
                .show()
        } else {
            onPlace(index, view)
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
