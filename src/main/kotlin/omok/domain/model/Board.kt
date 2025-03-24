package omok.domain.model

import omok.domain.model.position.Stone
import omok.domain.model.stone.Stones

class Board(
    val stones: Stones = Stones(listOf()),
    val size: Int = DEFAULT_BOARD_SIZE,
) {
    fun addedBoard(stone: Stone): Board = Board(stones + stone, size)

    fun inRange(value: Int) = value in 1..size

    companion object {
        private const val DEFAULT_BOARD_SIZE = 15
    }
}
