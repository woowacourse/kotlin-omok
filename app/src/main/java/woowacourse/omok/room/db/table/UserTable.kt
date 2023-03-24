package woowacourse.omok.room.db.table

import woowacourse.omok.room.db.Column
import woowacourse.omok.room.db.SQLiteTable

object UserTable : SQLiteTable {
    const val ID = "id"
    const val NAME = "name"

    override val name: String = "user"
    override val scheme: List<Column> = listOf(
        Column(ID, "INTEGER PRIMARY KEY AUTOINCREMENT"),
        Column(NAME, "varchar(255)")
    )
}
