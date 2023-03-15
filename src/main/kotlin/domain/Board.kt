package domain

import domain.turn.BlackTurn
import domain.turn.WhiteTurn

class Board(
    private val blackTurn: BlackTurn,
    private val whiteTurn: WhiteTurn
) {
    fun canMove(stone: Stone): Boolean {
        return blackTurn.isEmpty(stone) && whiteTurn.isEmpty(stone)
    }
}
