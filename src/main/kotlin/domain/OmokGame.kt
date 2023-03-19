package domain

import domain.board.Board
import domain.board.PlayingBoard
import domain.judgement.FiveStoneWinningConditionChecker
import domain.judgement.ForbiddenPositionChecker
import domain.judgement.RenjuRuleForbiddenPositionChecker
import domain.judgement.WinningConditionChecker
import domain.stone.Color
import domain.stone.Position
import domain.stone.Stone

class OmokGame(
    val getPosition: (latestStone: Stone?) -> Position,
    val checkBoardState: (Board) -> Unit,
) {
    fun playOmokGameAndReturnWinner(
        winningConditionChecker: WinningConditionChecker = FiveStoneWinningConditionChecker(),
        forbiddenPositionChecker: ForbiddenPositionChecker = RenjuRuleForbiddenPositionChecker()
    ): Color {
        var board: Board = PlayingBoard(listOf(), winningConditionChecker, forbiddenPositionChecker)
        var turnColor = Color.BLACK
        while (board.isFinished.not()) {
            checkBoardState(board)
            board = turnGame(board, turnColor)
            turnColor = !turnColor
        }
        checkBoardState(board)
        return board.winningColor
    }

    private tailrec fun turnGame(board: Board, turnColor: Color): Board {
        val position = getPosition(board.getLatestStone())
        if (board.isPossiblePut(position)) {
            return board.addStone(Stone(position, turnColor))
        }
        return turnGame(board, turnColor)
    }
}
