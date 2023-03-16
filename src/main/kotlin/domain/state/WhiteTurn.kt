package domain.state

import domain.rule.OmokRule
import domain.stone.Board
import domain.stone.Stone
import domain.stone.StoneType

class WhiteTurn(board: Board) : Running(board) {
    override fun put(stone: Stone): State {
        if (!isValidPut(stone)) return WhiteTurn(board)
        board.putStone(stone)
        if (OmokRule.isWinCondition(board.board, stone)) return End(StoneType.WHITE)
        return BlackTurn(board)
    }
}
