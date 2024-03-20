package omok.model

object Board {
    const val BOARD_SIZE = 15

    val board = Array(BOARD_SIZE) { Array(BOARD_SIZE) { Stone.NONE } }
    lateinit var lastPosition: Position

    fun getLastStonePosition(): Position? {
        if (::lastPosition.isInitialized) return lastPosition
        return null
    }
}
