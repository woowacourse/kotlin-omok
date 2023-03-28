package domain.state.running

import domain.state.State
import domain.state.end.End
import domain.stone.Board
import domain.stone.Stone
import domain.stone.StonePosition
import domain.stone.StoneType

class WhiteTurn : Running() {
    override fun next(board: Board, stonePosition: StonePosition): State {
        val stone: Stone = Stone(stonePosition, StoneType.WHITE)

        if (isPlaced(board, stonePosition)) return this
        board.putStone(stone)

        if (omokRule.isWinCondition(board.board, stone)) return End(StoneType.WHITE)

        return BlackTurn()
    }
}
