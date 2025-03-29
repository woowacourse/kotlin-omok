package woowacourse.omok.data

import woowacourse.omok.domain.stone.Stone

class OmokRepository(
    private val stoneDataSource: StoneDataSource,
) {
    fun insert(stone: Stone) {
        stoneDataSource.insert(StoneDao.valueOf(stone))
    }
}
