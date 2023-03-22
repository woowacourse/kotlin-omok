package domain.listener

import domain.OmokBoard
import domain.State
import domain.Stone

interface OmokListener {
    fun onStoneRequest(): Stone
    fun onMove(omokBoard: OmokBoard, state: State, stone: Stone)
    fun onMoveFail()
    fun onForbidden()
    fun onFinish(state: State): State
}
