package domain

import domain.judgement.RenjuRule
import domain.judgement.Rule
import domain.stone.Color
import domain.stone.Position
import domain.stone.Stone
import domain.turn.StartBoardState
import domain.turn.Turn

class OmokGame(
    rule: Rule = RenjuRule()
) {
    private var turn: Turn = Turn(Color.BLACK, StartBoardState(rule))

    val isFinished: Boolean
        get() = turn.boardState.isFinished()

    val turnColor: Color
        get() = turn.color

    val board: Map<Position, Color?>
        get() = turn.boardState.board

    val latestStone: Stone?
        get() = turn.boardState.latestStone

    val winnerColor: Color?
        get() = turn.winnerColor

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
