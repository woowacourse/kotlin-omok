package omok.domain

import omok.domain.judgement.FourJudgement
import omok.domain.judgement.LineJudgement
import omok.domain.judgement.ThreeJudgement
import omok.domain.state.Turn

class Board(val blackPlayer: Player, val whitePlayer: Player) {
    private val positions = Position.POSITIONS.toList()

    fun isPlaceable(turn: Turn, position: Position): Boolean {
        return when {
            turn == Turn.White -> positions.find { it == position }?.isEmpty() == true
            LineJudgement(blackPlayer, position).check() -> true
            ThreeJudgement(blackPlayer, whitePlayer, position).check() || FourJudgement(blackPlayer, whitePlayer, position).check() -> false
            else -> positions.find { it == position }?.isEmpty() == true
        }
    }

    fun putStone(turn: Turn, position: Position) {
        when (turn) {
            Turn.Black -> blackPlayer.put(Stone(position))
            Turn.White -> whitePlayer.put(Stone(position))
        }
        positions.find { it == position }?.occupy()
    }

    fun lineJudge(turn: Turn, position: Position): Boolean {
        return when (turn) {
            Turn.Black -> LineJudgement(blackPlayer, position).check()
            Turn.White -> LineJudgement(whitePlayer, position).check()
        }
    }
}
