package woowacourse.omok.db.table

import woowacourse.omok.db.Column
import woowacourse.omok.db.SQLiteTable

data class GameStonesTable(
    override val name: String = "gameStones",
    override val scheme: List<Column> = listOf(
        Column("gameId", "int"),
        Column("stoneId", "int"),
    )
) : SQLiteTable