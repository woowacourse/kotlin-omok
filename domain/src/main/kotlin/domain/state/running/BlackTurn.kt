package domain.state.running

import domain.state.State
import domain.state.end.End
import domain.stone.Board
import domain.stone.Stone
import domain.stone.StonePosition
import domain.stone.StoneType

class BlackTurn : Running() {
    override fun next(board: Board, stonePosition: StonePosition): State {
        val stone: Stone = Stone(stonePosition, StoneType.BLACK)

        if (!isValidPut(board, stonePosition)) return this
        if (omokRule.isForbidden(board.board, stone)) return this
        board.putStone(stone)

        if (omokRule.isWinCondition(board.board, stone)) return End(StoneType.BLACK)

        return WhiteTurn()
    }
}
