package domain.state.running

import domain.state.State
import domain.state.end.End
import domain.stone.Board
import domain.stone.Stone
import domain.stone.StonePosition
import domain.stone.StoneType

class WhiteTurn(board: Board) : Running(board) {
    override fun next(stonePosition: StonePosition): State {
        val stone: Stone = Stone(stonePosition, StoneType.WHITE)

        if (!isValidPut(stonePosition)) return WhiteTurn(board)
        board.putStone(stone)

        if (omokRule.isWinCondition(board.board, stone)) return End(StoneType.WHITE)

        return BlackTurn(board)
    }
}
