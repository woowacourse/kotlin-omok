package model.domain

import model.domain.state.Omok
import model.domain.state.State
import model.domain.state.black.BlackTurn
import model.domain.tools.Board
import model.domain.tools.Coordination
import model.domain.tools.Location
import model.domain.tools.Stone

class OmokGame(private val board: Board) {
    private var state: State = BlackTurn()

    fun start(
        getCoordination: (Stone) -> Pair<Int, Int>,
        updateBoard: (Board) -> Unit
    ) {
        while (state !is Omok) {
            updateBoard(board)
            state = play(getCoordination, state)
        }
    }

    private fun play(getCoordination: (Stone) -> Pair<Int, Int>, state: State): State {
        val value = getCoordination(state.stone)
        val location = Location(Coordination.from(value.first), Coordination.from(value.second))

        return state.place(location, board)
    }

    fun getWinner(findWinner: (Stone) -> Unit, updateBoard: (Board) -> Unit) {
        findWinner(state.stone)
        updateBoard(board)
    }
}
