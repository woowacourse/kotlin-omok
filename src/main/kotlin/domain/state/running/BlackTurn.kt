package domain.state.running

import domain.state.State
import domain.state.end.End
import domain.stone.Board
import domain.stone.Stone
import domain.stone.StonePosition
import domain.stone.StoneType

class BlackTurn(board: Board) : Running(board) {
    override fun next(stonePosition: StonePosition): State {
        val stone: Stone = Stone(stonePosition, StoneType.BLACK)

        if (!isValidPut(stonePosition)) return BlackTurn(board)
        if (omokRule.isForbidden(board.board, stone)) return BlackTurn(board)
        board.putStone(stone)

        if (omokRule.isWinCondition(board.board, stone)) return End(StoneType.BLACK)

        return WhiteTurn(board)
    }
}
