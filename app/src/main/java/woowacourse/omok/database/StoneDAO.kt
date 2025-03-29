package woowacourse.omok.database

import omok.domain.Stone

interface StoneDAO {
    fun insertStone(
        row: Int,
        column: Int,
        color: String,
    )

    fun queryStones(): List<Stone>

    fun clear()
}
