package domain

import domain.board.Board

interface Listener {
    fun onStoneRequest(): Stone
    fun onMove(board: Board, state: State, stone: Stone)
    fun onMoveFail()
    fun onForbidden()
    fun onFinish(state: State)
}
