package domain.state

import domain.rule.OmokRule
import domain.stone.Board
import domain.stone.Stone
import domain.stone.StoneType

class BlackTurn(board: Board) : Running(board) {
    override fun put(stone: Stone): State {
        if (!isValidPut(stone)) return BlackTurn(board)
        if (checkForbidden(board, stone)) return BlackTurn(board)
        board.putStone(stone)
        if (isOmokCondition(stone)) return End(StoneType.BLACK)
        return WhiteTurn(board)
    }

    private fun checkForbidden(board: Board, stone: Stone): Boolean {
        return OmokRule.countOpenThrees(board.board, stone) >= OmokRule.MIN_OPEN_THREES ||
            OmokRule.countOpenFours(board.board, stone) >= OmokRule.MIN_OPEN_FOURS
    }
}
