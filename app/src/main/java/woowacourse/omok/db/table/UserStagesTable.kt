package woowacourse.omok.db.table

import woowacourse.omok.db.Column
import woowacourse.omok.db.SQLiteTable

object UserStagesTable : SQLiteTable {
    const val USER_ID = "userId"
    const val STAGE_ID = "stageId"

    override val name: String = "userStages"
    override val scheme: List<Column> = listOf(
        Column(USER_ID, "int"),
        Column(STAGE_ID, "int")
    )
}
