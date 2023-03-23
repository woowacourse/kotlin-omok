package domain.state.end

import domain.state.State
import domain.stone.Board
import domain.stone.StonePosition
import domain.stone.StoneType

class End(private val stoneType: StoneType) : State {

    fun getWinner(): StoneType = stoneType

    override fun next(board: Board, stonePosition: StonePosition): State = this
    override fun isEnd(): Boolean = true
}
