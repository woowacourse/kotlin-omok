package woowacourse.omok.room.db.table

import woowacourse.omok.room.db.SQLColumn
import woowacourse.omok.room.db.SQLType
import woowacourse.omok.room.db.SQLiteTable

object StageTable : SQLiteTable {
    const val ID = "id"

    override val name: String = "stage"
    override val scheme: List<SQLColumn> = listOf(
        SQLColumn(ID, SQLType.INTEGER, "PRIMARY KEY AUTOINCREMENT")
    )
}
