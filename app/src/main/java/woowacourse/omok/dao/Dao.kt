package woowacourse.omok.dao

import woowacourse.omok.entity.LatestStoneEntity
import woowacourse.omok.entity.OmokBoardEntity

interface Dao {
    fun insertBoard(item: LatestStoneEntity): Long

    fun findLatestStoneByNickName(nickname: String): LatestStoneEntity?

    fun updateBoard(item: LatestStoneEntity): Int

    fun findBoardByNickName(nickname: String): OmokBoardEntity?
}
