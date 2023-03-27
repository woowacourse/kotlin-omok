package woowacourse.omok.room.db.table

import woowacourse.omok.room.db.SQLColumn
import woowacourse.omok.room.db.SQLiteTable

object StoneTable : SQLiteTable {
    const val ID = "id"
    const val X = "x"
    const val Y = "y"
    const val COLOR = "color"

    override val name: String = "stone"
    override val scheme: List<SQLColumn> = listOf(
        SQLColumn(ID, "INTEGER PRIMARY KEY AUTOINCREMENT"),
        SQLColumn(X, "int"),
        SQLColumn(Y, "int"),
        SQLColumn(COLOR, "int"),
    )
}
