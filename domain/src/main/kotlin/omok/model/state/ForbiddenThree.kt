package omok.model.state

import omok.model.game.Board

class ForbiddenThree(board: Board) : State(board) {
    override var isRunning = true
    override var isForbidden = true
}
