package model.domain

import model.domain.state.State
import model.domain.tools.Dot
import model.domain.tools.Location

class OmokGame {
    fun playNextTurn(state: State, dot: Dot): State {
        val location: Location = getLocation(dot)

        if (state.board.canPlace(location)) return state.place(location)
        return state
    }

    private fun getLocation(dot: Dot): Location = Location(dot.row, dot.col)

    companion object {
        const val BOARD_SIZE = 15
    }
}
