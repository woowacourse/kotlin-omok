package woowacourse.omok.listener

import android.content.Context
import android.widget.Toast
import domain.OmokBoard
import domain.State
import domain.Stone
import domain.listener.OmokListener
import view.OutputView

class OmokGameListener(private val context: Context) : OmokListener {
    override fun onStoneRequest(): Stone {
        TODO("Not yet implemented")
    }

    override fun onMove(omokBoard: OmokBoard, state: State, stone: Stone) {
        OutputView().printOmokState(omokBoard, state, stone)
    }

    override fun onMoveFail() {
        Toast.makeText(context, "이미 돌이 존재합니다.", Toast.LENGTH_SHORT).show()
    }

    override fun onForbidden() {
        Toast.makeText(context, "그곳은 금수 입니다.", Toast.LENGTH_SHORT).show()
    }

    override fun onFinish(state: State): State {
        Toast.makeText(context, "${state.name}승!", Toast.LENGTH_SHORT).show()
        return state
    }
}
