package domain

import domain.turn.BlackTurn
import domain.turn.WhiteTurn

class Board(
    val blackTurn: BlackTurn,
    val whiteTurn: WhiteTurn
) {
    fun canMove(stone: Stone): Boolean {
        return blackTurn.isEmpty(stone) && whiteTurn.isEmpty(stone)
    }

    fun moveWhite(stone: Stone) {
        whiteTurn.move(stone)
    }

    fun moveBlack(stone: Stone) {
        blackTurn.move(stone)
    }
}
