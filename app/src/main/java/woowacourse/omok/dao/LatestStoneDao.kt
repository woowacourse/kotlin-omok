package woowacourse.omok.dao

import woowacourse.omok.entity.LatestStoneEntity

interface LatestStoneDao {
    fun findLatestStoneByNickName(nickname: String): LatestStoneEntity?

    fun updateBoard(item: LatestStoneEntity): Int
}
