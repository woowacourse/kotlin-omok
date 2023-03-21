package domain

interface OmokGameListener {
    fun onMove(omokBoard: OmokBoard, state: State, stone: Stone)
    fun onMoveFail()
    fun onForbidden()
    fun onFinish(state: State)
}
