package woowacourse.omok.room.db.table

import woowacourse.omok.room.db.SQLColumn
import woowacourse.omok.room.db.SQLiteTable

object UserStagesTable : SQLiteTable {
    const val USER_ID = "userId"
    const val STAGE_ID = "stageId"

    override val name: String = "userStages"
    override val scheme: List<SQLColumn> = listOf(
        SQLColumn(USER_ID, "int"),
        SQLColumn(STAGE_ID, "int")
    )
}
