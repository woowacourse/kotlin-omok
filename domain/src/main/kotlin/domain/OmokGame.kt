package domain

import domain.judgement.RenjuRule
import domain.judgement.Rule
import domain.stone.Color
import domain.stone.Position
import domain.turn.RunningBoardState
import domain.turn.Turn

class OmokGame(
    board: Map<Position, Color?> = mapOf(),
    rule: Rule = RenjuRule()
) {
    var turn: Turn = Turn(Color.BLACK, RunningBoardState(rule, board))
        private set

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
