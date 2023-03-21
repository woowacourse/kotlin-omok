package domain

interface OmokGameListener {
    fun onStoneRequest(): Stone
    fun onMove(omokBoard: OmokBoard, state: State, stone: Stone)
    fun onMoveFail()
    fun onForbidden()
    fun onFinish(state: State)
}
