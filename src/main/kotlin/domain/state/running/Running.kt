package domain.state.running

import domain.rule.OmokRule
import domain.rule.RenjuOmokRule
import domain.state.State
import domain.stone.Board
import domain.stone.StonePosition
import domain.stone.StoneType

abstract class Running(val board: Board) : State {

    val omokRule: OmokRule = RenjuOmokRule()

    abstract override fun next(stonePosition: StonePosition): State

    override fun getWinner(): StoneType = StoneType.EMPTY

    protected fun isValidPut(stonePosition: StonePosition): Boolean =
        !board.stones.containsPosition(stonePosition)
}
