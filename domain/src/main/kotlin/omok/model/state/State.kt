package omok.model.state

import omok.model.game.Board

abstract class State(board: Board) {
    abstract var isRunning: Boolean
    abstract var isForbidden: Boolean
}
