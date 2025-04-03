package woowacourse.omok.data.db

import woowacourse.omok.domain.Stone

class BoardDao(
    private val dbHelper: DbHelper,
) {
    fun insert(
        stone: Stone,
        gameId: Long,
    ) = dbHelper.insertStone(stone, gameId)

    fun queryStones(gameId: Long): List<Stone> = dbHelper.queryStones(gameId)
}
