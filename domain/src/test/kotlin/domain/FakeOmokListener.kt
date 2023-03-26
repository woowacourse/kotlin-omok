package domain

import domain.listener.OmokListener

open class FakeOmokListener : OmokListener {
    override fun onMove(omokBoard: OmokBoard, state: State, stone: Stone) {}

    override fun onMoveFail() {}

    override fun onForbidden() {}

    override fun onFinish(state: State): State {
        return state
    }
}
