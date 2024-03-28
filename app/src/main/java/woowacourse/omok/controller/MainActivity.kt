package woowacourse.omok.controller

import android.os.Bundle
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import omok.model.Board
import omok.model.Position
import omok.model.rule.RenjuRule
import omok.model.state.BlackTurn
import omok.model.state.GameState
import woowacourse.omok.R
import woowacourse.omok.util.convertIndexToPosition
import woowacourse.omok.view.OmokView
import woowacourse.omok.util.showToast
import woowacourse.omok.view.OmokBoardView

class MainActivity : AppCompatActivity(), OmokController {
    private var gameState: GameState = BlackTurn(RenjuRule, Board(emptyMap()))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
    }

    private fun initView() {
        setContentView(R.layout.activity_main)
        initBoardView()
    }

    private fun initBoardView() {
        val boardView = makeBoardView()
        boardView.forEachIndexed { index, view ->
            view.setOnClickListener {
                placeOmokStone(index, view)
            }
        }
    }

    private fun makeBoardView() =
        findViewById<TableLayout>(R.id.board)
            .children
            .filterIsInstance<TableRow>()
            .flatMap { it.children }
            .filterIsInstance<ImageView>()

    private fun placeOmokStone(
        index: Int,
        view: ImageView,
    ) {
        runCatching {
            onPlace(OmokBoardView(view), convertIndexToPosition(index))
        }.onFailure {
            showToast(this, "${it.message}")
        }
    }

    override fun onPlace(
        view: OmokView,
        position: Position,
    ) {
        gameState = gameState.put(position)
        view.updateBoard(position, gameState)
    }
}
