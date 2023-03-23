package woowacourse.omok.db.table

import woowacourse.omok.db.Column
import woowacourse.omok.db.SQLiteTable

object UserStagesTable : SQLiteTable {
    override val name: String = "userStages"
    override val scheme: List<Column> = listOf(
        Column("userId", "int"),
        Column("stageId", "int")
    )
}