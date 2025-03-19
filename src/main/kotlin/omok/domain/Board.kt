package omok.domain

class Board {
    val grid: Array<Array<StoneColor>> = Array(BOARD_SIZE) { Array(BOARD_SIZE, {StoneColor.EMPTY})}

    companion object {
        private const val BOARD_SIZE = 15
    }
}