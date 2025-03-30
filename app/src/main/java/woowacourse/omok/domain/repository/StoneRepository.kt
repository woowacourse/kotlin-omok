package woowacourse.omok.domain.repository

import woowacourse.omok.domain.model.Board
import woowacourse.omok.domain.model.stone.Stone
import woowacourse.omok.domain.model.stone.StoneType
import woowacourse.omok.domain.model.stone.Stones

interface StoneRepository {
    fun insert(stone: Stone)

    fun lastStoneType(board: Board): StoneType

    fun allInBoardSize(board: Board): Stones

    fun clear()
}
