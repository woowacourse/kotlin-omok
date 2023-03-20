package model.domain

import model.domain.state.State
import model.domain.tools.Coordination
import model.domain.tools.Dot
import model.domain.tools.Location
import model.domain.tools.Stone

class OmokGame {
    fun playNextTurn(state: State, getDot: (Stone) -> Dot): State {
        val location: Location = getLocation(state, getDot)

        if (state.board.canPlace(location)) return state.place(location)
        return state
    }

    private fun getLocation(state: State, getDot: (Stone) -> Dot): Location = runCatching {
        val dot = getDot(state.stone)

        Location(Coordination.from(dot.row), Coordination.from(dot.col))
    }.getOrNull() ?: getLocation(state, getDot)
}
