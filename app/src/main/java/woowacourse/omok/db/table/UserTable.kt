package woowacourse.omok.db.table

import woowacourse.omok.db.Column
import woowacourse.omok.db.SQLiteTable

data class UserTable(
    override val name: String = "user",
    override val scheme: List<Column> = listOf(
        Column("id", "int"),
        Column("name", "varchar(255)")
    )
) : SQLiteTable {}