package woowacourse.omok.dao

import woowacourse.omok.entity.LatestStoneEntity
import woowacourse.omok.entity.OmokBoardEntity

interface OmokPlaceDao {
    fun insertBoard(item: LatestStoneEntity): Long

    fun findBoardByNickName(nickname: String): OmokBoardEntity?
}
