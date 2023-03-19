package domain

import domain.board.PlacedBoard
import domain.judgement.FiveStoneWinningCondition
import domain.judgement.ForbiddenCondition
import domain.judgement.RenjuRuleForbiddenCondition
import domain.judgement.WinningCondition
import domain.stone.Color
import domain.stone.Position
import domain.turn.BlackTurn
import domain.turn.Turn

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
            var (nextTurn, newPosition) = turnGame(turn, latestPosition)
            turn = nextTurn
            latestPosition = newPosition
        }
        checkBoardState(turn.getBoard())
        return turn.curColor
    }

    private tailrec fun turnGame(curTurn: Turn, latestPosition: Position?): Pair<Turn, Position?> {
        val position = getPosition(curTurn.curColor, latestPosition)
        val nextTurn = curTurn.addStone(position)
        if (curTurn !== nextTurn || nextTurn.isFinished) {
            return Pair(nextTurn, position)
        }
        return turnGame(curTurn, latestPosition)
    }
}
