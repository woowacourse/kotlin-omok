package woowacourse.omok.data.`interface`

import woowacourse.omok.data.OmokEntity
import woowacourse.omok.data.adapter.StonePosition
import woowacourse.omok.model.board.Position
import woowacourse.omok.model.board.Stone

interface OmokDataStore {
    fun add(position: Position, stone: Stone)

    fun reset()

    fun save(isFinish: Boolean)

    fun lastStonePosition(): StonePosition?

    fun omokEntities(): List<OmokEntity>
}