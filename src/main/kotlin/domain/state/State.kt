package domain.state

import domain.stone.Board
import domain.stone.StonePosition
import domain.stone.StoneType

interface State {

    fun next(board: Board, stonePosition: StonePosition): State

    fun getWinner(): StoneType?
}
