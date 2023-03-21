package omok

import omok.judgement.Judgement
import omok.state.Turn

class Board(private val blackPlayer: Player, private val whitePlayer: Player) {
    private val positions: List<Position> = Position.POSITIONS.toList()

    fun isPlaceable(turn: Turn, position: Position): Boolean {
        if (turn == Turn.White) {
            return isEmpty(position)
        }
        return isEmpty(position) && !Judgement.isForbiddenMove(blackPlayer, whitePlayer, position)
    }

    private fun isEmpty(position: Position) = positions.find { it == position }?.isEmpty() == true

    fun putStone(turn: Turn, position: Position) {
        when (turn) {
            Turn.Black -> blackPlayer.put(Stone(position))
            Turn.White -> whitePlayer.put(Stone(position))
        }
        positions.find { it == position }?.occupy()
    }

    fun lineJudge(turn: Turn, position: Position): Boolean {
        return when (turn) {
            Turn.Black -> Judgement.line(blackPlayer, position)
            Turn.White -> Judgement.line(whitePlayer, position)
        }
    }
}
