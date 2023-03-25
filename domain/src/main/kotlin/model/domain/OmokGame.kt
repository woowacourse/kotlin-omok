package model.domain

import model.domain.state.State
import model.domain.state.black.BlackTurn
import model.domain.tools.Board
import model.domain.tools.Coordination
import model.domain.tools.Location
import model.domain.tools.Stone

class OmokGame(private val board: Board) {
    private var _state: State = BlackTurn()
    val state: State get() = _state

    fun start(
        updateBoard: (Board) -> Unit,
        getStone: (Stone) -> Unit,
    ) {
        updateBoard(board)
        getStone(_state.stoneColor)
    }

    fun play(getCoordination: () -> Pair<Int, Int>) {
        val value = getCoordination()
        val location = Location(Coordination.from(value.first), Coordination.from(value.second))

        _state = _state.place(location, board)
    }

    fun getWinner(findWinner: (Stone) -> Unit, updateBoard: (Board) -> Unit) {
        findWinner(_state.stoneColor)
        updateBoard(board)
    }
}
