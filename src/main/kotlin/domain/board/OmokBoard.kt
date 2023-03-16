package domain.board

import domain.State
import domain.Stone

class OmokBoard(
    val board: Board = Board()
) {
    fun move(stone: Stone, state: State) {
        board.move(stone, state)
    }

    fun isEmpty(stone: Stone): Boolean = board.isEmpty(stone)
}
