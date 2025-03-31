package woowacourse.omok

import woowacourse.omok.data.OmokDao
import woowacourse.omok.data.StateContract.BLACK_STONES_TABLE
import woowacourse.omok.data.StateContract.WHITE_STONES_TABLE
import woowacourse.omok.domain.model.Point
import woowacourse.omok.domain.model.state.Finished
import woowacourse.omok.domain.model.state.Foul
import woowacourse.omok.domain.model.state.Playing
import woowacourse.omok.domain.model.state.State
import woowacourse.omok.domain.model.stone.Stones

class OmokGameManager {
    fun updateState(
        state: State,
        point: Point,
        omokDao: OmokDao,
    ): State {
        if (state !is Playing) return state

        val newState = state.place(point, 15) { _, _ -> }

        return when (newState) {
            is Foul -> newState
            is Finished -> {
                omokDao.saveGameState(newState)
                newState
            }
            is Playing -> {
                omokDao.saveGameState(newState)
                saveStones(state.blackStones, BLACK_STONES_TABLE, omokDao)
                saveStones(state.whiteStones, WHITE_STONES_TABLE, omokDao)
                newState
            }
        }
    }

    fun saveStones(
        stones: Stones,
        tableName: String,
        omokDao: OmokDao,
    ) {
        stones.points.forEach { point ->
            omokDao.saveStone(tableName, point)
        }
    }
}
