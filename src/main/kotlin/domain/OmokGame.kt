package domain

import domain.board.BlackTurn
import domain.board.PlacedBoard
import domain.board.Turn
import domain.judgement.FiveStoneWinningCondition
import domain.judgement.ForbiddenCondition
import domain.judgement.RenjuRuleForbiddenCondition
import domain.judgement.WinningCondition
import domain.stone.Color
import domain.stone.Position

class OmokGame(
    val getPosition: (turnColor: Color, latestPosition: Position?) -> Position,
    val checkBoardState: (board: Map<Position, Color?>) -> Unit,
) {
    fun playOmokGameAndReturnWinner(
        winningCondition: WinningCondition = FiveStoneWinningCondition(),
        forbiddenCondition: ForbiddenCondition = RenjuRuleForbiddenCondition()
    ): Color {
        var turn: Turn = BlackTurn(PlacedBoard(), winningCondition, forbiddenCondition)
        var latestPosition: Position? = null
        while (turn.isFinished.not()) {
            checkBoardState(turn.getBoard())
            val turnResult = turnGame(turn, latestPosition)
            turn = turnResult.nextTurn
            latestPosition = turnResult.latestPosition
        }
        checkBoardState(turn.getBoard())
        return turn.curColor
    }

    private tailrec fun turnGame(curTurn: Turn, latestPosition: Position?): TurnResult {
        val position = getPosition(curTurn.curColor, latestPosition)
        val nextTurn = curTurn.addStone(position)
        if (curTurn !== nextTurn || nextTurn.isFinished) {
            return TurnResult(nextTurn, position)
        }
        return turnGame(curTurn, latestPosition)
    }

    private data class TurnResult(
        val nextTurn: Turn,
        val latestPosition: Position?
    )
}
