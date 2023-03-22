package woowacourse.omok.db.table

import woowacourse.omok.db.Column
import woowacourse.omok.db.SQLiteTable

data class UserGamesTable(
    override val name: String = "userGames",
    override val scheme: List<Column> = listOf(
        Column("userId", "int"),
        Column("gameId", "int")
    )
) : SQLiteTable