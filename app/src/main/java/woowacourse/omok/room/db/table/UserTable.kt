package woowacourse.omok.room.db.table

import woowacourse.omok.room.db.SQLColumn
import woowacourse.omok.room.db.SQLiteTable

object UserTable : SQLiteTable {
    const val ID = "id"
    const val NAME = "name"

    override val name: String = "user"
    override val scheme: List<SQLColumn> = listOf(
        SQLColumn(ID, "INTEGER PRIMARY KEY AUTOINCREMENT"),
        SQLColumn(NAME, "varchar(255)")
    )
}
