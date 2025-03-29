package woowacourse.omok.data

import woowacourse.omok.domain.position.Position
import woowacourse.omok.domain.stone.Stone

class StoneFakeDataSource : StoneDataSource {
    private val _stones = mutableListOf<Stone>()
    val stones get() = _stones.toList()

    init {
        _stones.clear()
    }

    override fun insert(stoneDao: StoneDao) {
        _stones.add(stoneDao.toStone())
    }

    override fun fetchStoneByPosition(position: Position): Stone? {
        return stones.firstOrNull { stone -> stone.position.isSame(position) }
    }

    private fun StoneDao.toStone(): Stone {
        return Stone(Position(this.row, this.col), this.stoneColor)
    }
}
