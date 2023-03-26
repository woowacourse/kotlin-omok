package woowacourse.omok.data.db.table

import woowacourse.omok.data.db.Column
import woowacourse.omok.data.db.SQLiteTable

object UserTable : SQLiteTable {
    const val ID = "id"
    const val NAME = "name"

    override val name: String = "stone"
    override val scheme: List<Column> = listOf(
        Column(ID, "INTEGER PRIMARY KEY AUTOINCREMENT"),
        Column(NAME, "TEXT")
    )
}
