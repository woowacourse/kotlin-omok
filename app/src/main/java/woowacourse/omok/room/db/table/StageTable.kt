package woowacourse.omok.room.db.table

import woowacourse.omok.room.db.Column
import woowacourse.omok.room.db.SQLiteTable

object StageTable : SQLiteTable {
    const val ID = "id"

    override val name: String = "stage"
    override val scheme: List<Column> = listOf(
        Column(ID, "INTEGER PRIMARY KEY AUTOINCREMENT")
    )
}
