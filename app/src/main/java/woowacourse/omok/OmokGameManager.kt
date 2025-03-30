package woowacourse.omok

import data.OmokDao
import data.StateContract.BLACK_STONES_TABLE
import data.StateContract.WHITE_STONES_TABLE
import domain.domain.Point
import domain.domain.state.Finished
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
            if (state !is Finished) {
                state.blackStones.points.forEach { point ->
                    omokDao.saveStone(BLACK_STONES_TABLE, point)
                }
                state.whiteStones.points.forEach { point ->
                    omokDao.saveStone(WHITE_STONES_TABLE, point)
                }
            }
        }
        return newState
    }
}
