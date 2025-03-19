package omok.domain

class Board {
    val grid: Array<Array<StoneType>> = Array(BOARD_SIZE) { Array(BOARD_SIZE, {StoneType.EMPTY})}

    companion object {
        private const val BOARD_SIZE = 15
    }
}