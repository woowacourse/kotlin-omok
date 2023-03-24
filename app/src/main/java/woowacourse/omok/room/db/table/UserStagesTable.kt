package woowacourse.omok.room.db.table

import woowacourse.omok.room.db.Column
import woowacourse.omok.room.db.SQLiteTable

object UserStagesTable : SQLiteTable {
    const val USER_ID = "userId"
    const val STAGE_ID = "stageId"

    override val name: String = "userStages"
    override val scheme: List<Column> = listOf(
        Column(USER_ID, "int"),
        Column(STAGE_ID, "int")
    )
}
