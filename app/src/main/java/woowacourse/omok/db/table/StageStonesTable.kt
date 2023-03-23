package woowacourse.omok.db.table

import woowacourse.omok.db.Column
import woowacourse.omok.db.SQLiteTable

object StageStonesTable : SQLiteTable {
    override val name: String = "stageStones"
    override val scheme: List<Column> = listOf(
        Column("stageId", "int"),
        Column("stoneId", "int"),
    )
}