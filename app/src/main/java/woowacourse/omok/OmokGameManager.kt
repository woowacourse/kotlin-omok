package woowacourse.omok

import woowacourse.omok.data.OmokDao
import woowacourse.omok.data.StateContract.BLACK_STONES_TABLE
import woowacourse.omok.data.StateContract.WHITE_STONES_TABLE
import woowacourse.omok.domain.model.Board
import woowacourse.omok.domain.model.Point
import woowacourse.omok.domain.model.state.State
import woowacourse.omok.domain.model.state.State.Finished
import woowacourse.omok.domain.model.state.State.Foul
import woowacourse.omok.domain.model.state.State.Playing
import woowacourse.omok.domain.model.stone.Stones

class OmokGameManager {
    fun updateState(
        board: Board,
        point: Point,
        omokDao: OmokDao,
    ): State {
        val state = board.state

        if (state !is Playing) return state

        val newState = board.place(state, point, 15) { _, _ -> }

        return when (newState) {
            is Foul -> newState
            is Finished -> {
                omokDao.saveGameState(newState)
                newState
            }
            is Playing -> {
                omokDao.saveGameState(newState)
                saveStones(newState.blackStones, BLACK_STONES_TABLE, omokDao)
                saveStones(newState.whiteStones, WHITE_STONES_TABLE, omokDao)
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
