package omok.model.board

import omok.model.position.Position
import omok.model.stone.StoneType

object Board {
    const val BOARD_SIZE = 15

    val board = Array(BOARD_SIZE) { Array(BOARD_SIZE) { StoneType.NONE } }

    private var _lastPosition: Position? = null
    val lastPosition: Position?
        get() = _lastPosition

    fun updateLastPosition(position: Position?) {
        _lastPosition = position
    }
}
