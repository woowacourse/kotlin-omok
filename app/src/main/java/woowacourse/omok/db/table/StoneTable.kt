package woowacourse.omok.db.table

import woowacourse.omok.db.Column
import woowacourse.omok.db.SQLiteTable

object StoneTable : SQLiteTable {
    override val name: String = "stone"
    override val scheme: List<Column> = listOf(
        Column("id", "int"),
        Column("x", "int"),
        Column("y", "int"),
        Column("color", "int"),
    )
}