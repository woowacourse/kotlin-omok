package woowacourse.omok

import android.app.Activity
import android.util.Log
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import androidx.core.view.children
import domain.CoordinateState
import domain.domain.Board
import domain.domain.state.BlackTurn
import domain.domain.state.BlackWin
import domain.domain.state.State
import domain.domain.state.WhiteTurn
import domain.domain.state.WhiteWin
import domain.view.Observer
import woowacourse.omok.ContextUtil.longToast
import woowacourse.omok.ContextUtil.shortToast
import woowacourse.omok.OmokGameMapper.toName

class OmokGameController(private val board: Board, private val activity: Activity) : Observer {
    private var preColor: CoordinateState = CoordinateState.BLACK

    fun run() {
        board.registerObserver(this)
        loopBoardTable { index, imageView -> clickPart(index, imageView) }
    }

    private fun clickPart(index: Int, imageView: ImageView) {
        imageView.setOnClickListener {
            val position = OmokGameMapper.indexToPosition(index)
            board.next(position)
        }
    }

    override fun update(state: State) {
        when (state) {
            is BlackTurn -> updateView(state)
            is WhiteTurn -> updateView(state)
            is BlackWin -> win(state)
            is WhiteWin -> win(state)
        }
    }

    private fun updateView(state: State) {
        Log.d("뷰 테스트", "현재 상태: $state")
        when (preColor == state.getTurn()) {
            true -> activity.shortToast("놓을 수 없는 수 입니다")
            false -> drawStone(state)
        }
    }

    private fun drawStone(state: State) {
        val stones = OmokGameMapper.mapStones(state)
        loopBoardTable { index, imageView ->
            val resId = OmokGameMapper.matchColor(stones[index])
            if (resId != null) imageView.setImageResource(resId)
        }
        preColor = state.getTurn()
    }

    private fun loopBoardTable(action: (Int, ImageView) -> Unit) {
        val boardTable = activity.findViewById<TableLayout>(R.id.board_table)
        boardTable
            .children
            .filterIsInstance<TableRow>()
            .flatMap { it.children }
            .filterIsInstance<ImageView>()
            .forEachIndexed { index, view -> action(index, view) }
    }

    private fun win(state: State) {
        activity.longToast("${state.getTurn().toName()}의 승리입니다!!!")
        drawStone(state)
    }
}
