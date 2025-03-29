package woowacourse.omok.data

import woowacourse.omok.domain.position.Position
import woowacourse.omok.domain.stone.Stone

interface StoneDataSource {
    fun fetchStoneByPosition(position: Position): Stone?

    fun insert(stoneDao: StoneDao)
}
