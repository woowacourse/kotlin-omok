package domain.state

import domain.stone.Board
import domain.stone.StonePosition

interface State {

    fun next(board: Board, stonePosition: StonePosition): State

    fun isEnd(): Boolean
}
