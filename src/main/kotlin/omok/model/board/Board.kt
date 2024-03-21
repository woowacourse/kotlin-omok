package omok.model.board

import omok.model.position.Position
import omok.model.stone.Stone

object Board {
    const val BOARD_SIZE = 15

    val board = Array(BOARD_SIZE) { Array(BOARD_SIZE) { Stone.NONE } }
    lateinit var lastPosition: Position

    fun getLastStonePosition(): Position? {
        if (Board::lastPosition.isInitialized) return lastPosition
        return null
    }
}
