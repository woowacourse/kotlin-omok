package domain.state.running

import domain.rule.OmokRule
import domain.state.end.End
import domain.state.State
import domain.stone.Board
import domain.stone.Stone
import domain.stone.StoneType

class WhiteTurn(board: Board) : Running(board) {
    override fun next(stone: Stone): State {
        if (!isValidPut(stone)) return WhiteTurn(board)
        board.putStone(stone)
        if (OmokRule.isWinCondition(board.board, stone)) return End(StoneType.WHITE)
        return BlackTurn(board)
    }
}
