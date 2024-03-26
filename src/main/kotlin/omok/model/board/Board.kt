package omok.model.board

import omok.model.position.Position
import omok.model.stone.StoneType

object Board {
    private const val BOARD_SIZE = 15
    const val MIN_AXIS = 0
    const val MAX_AXIS = BOARD_SIZE - 1
    val axisRange = MIN_AXIS..MAX_AXIS

    val board = Array(BOARD_SIZE) { Array(BOARD_SIZE) { StoneType.NONE } }

    private var _lastPosition: Position? = null
    val lastPosition: Position?
        get() = _lastPosition

    fun updateLastPosition(position: Position?) {
        _lastPosition = position
    }

    fun reset() {
        repeat(BOARD_SIZE) { row ->
            repeat(BOARD_SIZE) { column ->
                board[row][column] = StoneType.NONE
            }
        }
    }
}
