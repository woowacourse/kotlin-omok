package woowacourse.omok.dao

import woowacourse.omok.entity.OmokBoardEntity

interface Dao {
    fun updateBoard(item: OmokBoardEntity): Int

    fun insertBoard(item: OmokBoardEntity): Long

    fun findBoardByNickName(nickname: String): OmokBoardEntity?
}
