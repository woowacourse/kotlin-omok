package woowacourse.omok

import woowacourse.omok.data.OmokDao
import woowacourse.omok.data.StateContract.BLACK_STONES_TABLE
import woowacourse.omok.data.StateContract.WHITE_STONES_TABLE
import woowacourse.omok.domain.model.Point
import woowacourse.omok.domain.model.state.Finished
import woowacourse.omok.domain.model.state.Foul
import woowacourse.omok.domain.model.state.Playing
import woowacourse.omok.domain.model.state.State

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
