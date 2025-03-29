package woowacourse.omok.domain.model

import woowacourse.omok.domain.model.position.Stone
import woowacourse.omok.domain.model.stone.Stones

interface StoneRepository {
    fun insert(stone: Stone)

    fun getAllInBoardSize(size: Int): Stones

    fun clear()
}
