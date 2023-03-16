package domain.state

import domain.stone.Board
import domain.stone.Stone
import domain.stone.StoneType

class BlackTurn(board: Board) : Running(board) {
    override fun put(stone: Stone): State {
        if (!isValidPut(stone)) return BlackTurn(board)
        board.putStone(stone)
        if (isOmokCondition(stone)) return End(StoneType.BLACK)
        return WhiteTurn(board)
    }
}
