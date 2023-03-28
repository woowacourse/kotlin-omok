package woowacourse.omok.room.db.table

import woowacourse.omok.room.db.SQLColumn
import woowacourse.omok.room.db.SQLType
import woowacourse.omok.room.db.SQLiteTable

object StoneTable : SQLiteTable {
    const val ID = "id"
    const val X = "x"
    const val Y = "y"
    const val COLOR = "color"

    override val name: String = "stone"
    override val scheme: List<SQLColumn> = listOf(
        SQLColumn(ID, SQLType.INTEGER, "PRIMARY KEY AUTOINCREMENT"),
        SQLColumn(X, SQLType.INTEGER),
        SQLColumn(Y, SQLType.INTEGER),
        SQLColumn(COLOR, SQLType.INTEGER),
    )
}
