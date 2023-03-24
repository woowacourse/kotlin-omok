package omok.model.state

import omok.model.game.Board

class Win(board: Board) : State(board) {
    override var isRunning = false
    override var isForbidden = false
}
