package omok.domain

import omok.domain.judgement.LineJudgement

class Board(val blackPlayer: Player, val whitePlayer: Player) {
    private val positions = Position.POSITIONS.toList()

    fun isBlackPlaceable(position: Position): Boolean {
        val stone = BlackStone(position)
        return when {
            LineJudgement(blackPlayer, stone).check() -> true
            stone.judgePossibility(blackPlayer, whitePlayer) -> false
            else -> positions.find { it == position }?.isEmpty() == true
        }
    }

    fun isWhitePlaceable(position: Position): Boolean {
        val stone = WhiteStone(position)
        return when {
            LineJudgement(whitePlayer, stone).check() -> true
            else -> positions.find { it == position }?.isEmpty() == true
        }
    }

    fun occupyPosition(position: Position) {
        positions.find { it == position }?.occupy()
    }
}
