package domain.state.running

import domain.rule.RenjuOmokRule
import domain.state.end.End
import domain.state.State
import domain.stone.Board
import domain.stone.Stone
import domain.stone.StoneType

class BlackTurn(board: Board) : Running(board) {
    override fun next(stone: Stone): State {
        if (!isValidPut(stone)) return BlackTurn(board)
        if (omokRule.isForbidden(board.board, stone)) return BlackTurn(board)
        board.putStone(stone)
        if (omokRule.isWinCondition(board.board, stone)) return End(StoneType.BLACK)
        return WhiteTurn(board)
    }
}
