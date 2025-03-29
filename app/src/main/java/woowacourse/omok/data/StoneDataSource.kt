package woowacourse.omok.data

import woowacourse.omok.domain.position.Position

interface StoneDataSource {
    fun fetchStoneByPosition(position: Position): StoneDao?

    fun insert(stoneDao: StoneDao)

    fun deleteAll()
}
