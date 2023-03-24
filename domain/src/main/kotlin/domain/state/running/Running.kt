package domain.state.running

import domain.rule.OmokRule
import domain.rule.RenjuOmokRule
import domain.state.State
import domain.stone.Board
import domain.stone.StonePosition

abstract class Running : State {

    val omokRule: OmokRule = RenjuOmokRule()

    abstract override fun next(board: Board, stonePosition: StonePosition): State

    override fun isEnd(): Boolean = false

    fun isPlaced(board: Board, stonePosition: StonePosition): Boolean =
        board.stones.containsPosition(stonePosition)
}
