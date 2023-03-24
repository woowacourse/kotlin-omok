package woowacourse.omok.db.table

import woowacourse.omok.db.Column
import woowacourse.omok.db.SQLiteTable

object StageStonesTable : SQLiteTable {
    const val STAGE_ID = "stageId"
    const val STONE_ID = "stoneId"

    override val name: String = "stageStones"
    override val scheme: List<Column> = listOf(
        Column(STAGE_ID, "int"),
        Column(STONE_ID, "int"),
    )
}
