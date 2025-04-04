package woowacourse.omok.domain.repository

import woowacourse.omok.domain.model.Board
import woowacourse.omok.domain.model.stone.Stone
import woowacourse.omok.domain.model.stone.StoneType
import woowacourse.omok.domain.model.stone.Stones

interface StoneRepository {
    fun insert(
        gameId: Long,
        stone: Stone,
    )

    fun lastStoneType(
        gameId: Long,
        board: Board,
    ): StoneType

    fun allInBoardSize(
        gameId: Long,
        board: Board,
    ): Stones

    fun clear(gameId: Long)
}
