package woowacourse.omok.domain.repository

import woowacourse.omok.domain.model.stone.Stone
import woowacourse.omok.domain.model.stone.StoneType
import woowacourse.omok.domain.model.stone.Stones

interface StoneRepository {
    fun insert(stone: Stone)

    fun lastStoneType(): StoneType

    fun allInBoardSize(size: Int): Stones

    fun clear()
}
