package woowacourse.omok.db.table

import woowacourse.omok.db.Column
import woowacourse.omok.db.SQLiteTable

object StageTable : SQLiteTable {
    override val name: String = "stage"
    override val scheme: List<Column> = listOf(
        Column("id", "int")
    )
}