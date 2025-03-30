package woowacourse.omok

import data.OmokDao
import domain.domain.Point
import domain.domain.state.Foul
import domain.domain.state.Playing
import domain.domain.state.State

class OmokGameManager {
    fun updateState(
        state: State,
        point: Point,
        omokDao: OmokDao,
    ): State {
        if (state !is Playing) return state

        val newState = state.place(point, 15) { _, _ -> }

        if (newState !is Foul) {
            omokDao.saveGameState(newState)
        }

        return newState
    }
}
