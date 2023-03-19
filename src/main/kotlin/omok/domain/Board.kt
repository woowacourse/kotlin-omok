package omok.domain

import omok.domain.judgement.LineJudgement
import omok.domain.state.Turn

class Board(val blackPlayer: Player, val whitePlayer: Player) {
    private val positions = Position.POSITIONS.toList()

    fun isPlaceable(turn: Turn, position: Position): Boolean {
        val blackStone = BlackStone(position)
        return when {
            LineJudgement(blackPlayer, blackStone).check() -> true
            blackStone.judgePossibility(blackPlayer, whitePlayer) -> false
            else -> turn == Turn.White || positions.find { it == position }?.isEmpty() == true
        }
    }

    fun occupyPosition(position: Position) {
        positions.find { it == position }?.occupy()
    }
}
