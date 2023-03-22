package woowacourse.omok.db.table

import woowacourse.omok.db.Column
import woowacourse.omok.db.SQLiteTable

data class GameTable(
    override val name: String = "game",
    override val scheme: List<Column> = listOf(
        Column("id", "int")
    )
) : SQLiteTable