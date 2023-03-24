package woowacourse.omok.db.table

import woowacourse.omok.db.Column
import woowacourse.omok.db.SQLiteTable

object UserTable : SQLiteTable {
    const val ID = "id"
    const val NAME = "name"

    override val name: String = "user"
    override val scheme: List<Column> = listOf(
        Column(ID, "INTEGER PRIMARY KEY AUTOINCREMENT"),
        Column(NAME, "varchar(255)")
    )
}
