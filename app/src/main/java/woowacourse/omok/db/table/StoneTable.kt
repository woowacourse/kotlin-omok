package woowacourse.omok.db.table

import woowacourse.omok.db.Column
import woowacourse.omok.db.SQLiteTable

object StoneTable : SQLiteTable {
    const val ID = "id"
    const val X = "x"
    const val Y = "y"
    const val COLOR = "color"

    override val name: String = "stone"
    override val scheme: List<Column> = listOf(
        Column(ID, "INTEGER PRIMARY KEY AUTOINCREMENT"),
        Column(X, "int"),
        Column(Y, "int"),
        Column(COLOR, "int"),
    )
}
