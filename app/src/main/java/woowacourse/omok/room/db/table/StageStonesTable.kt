package woowacourse.omok.room.db.table

import woowacourse.omok.room.db.Column
import woowacourse.omok.room.db.SQLiteTable

object StageStonesTable : SQLiteTable {
    const val STAGE_ID = "stageId"
    const val STONE_ID = "stoneId"

    override val name: String = "stageStones"
    override val scheme: List<Column> = listOf(
        Column(STAGE_ID, "int"),
        Column(STONE_ID, "int"),
    )
}
