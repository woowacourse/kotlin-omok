package domain

import domain.judgement.RenjuRule
import domain.judgement.Rule
import domain.stone.Color
import domain.stone.Position
import domain.stone.Stone
import domain.turn.RunningBoardState
import domain.turn.Turn

class OmokGame(
    board: Map<Position, Color?> = mapOf(),
    latestStone: Stone? = null,
    rule: Rule = RenjuRule()
) {
    var turn: Turn
        private set

    init {
        val blackCount = board.filter { it.value == Color.BLACK }.size
        val whiteCount = board.filter { it.value == Color.WHITE }.size
        turn = if (blackCount > whiteCount) {
            Turn(Color.WHITE, RunningBoardState(rule, board, latestStone))
        } else {
            Turn(Color.BLACK, RunningBoardState(rule, board, latestStone))
        }
    }

    fun playTurn(
        newPosition: Position
    ): Color? {
        val nextTurn = turn.putStone(newPosition)
        if (nextTurn === turn) return null
        val playTurnColor = turn.color
        turn = nextTurn
        return playTurnColor
    }
}
