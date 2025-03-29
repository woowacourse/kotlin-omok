package woowacourse.omok.db

import woowacourse.omok.entity.OmokBoardEntity

interface OmokDao {
    fun updateBoard(item: OmokBoardEntity): Int

    fun insertBoard(item: OmokBoardEntity): Long

    fun findBoardByNickName(nickname: String): OmokBoardEntity?
}
