package woowacourse.omok.data.db.table

import woowacourse.omok.data.db.Column
import woowacourse.omok.data.db.SQLiteTable

object GameTable : SQLiteTable {
    // 흑돌 백돌 변환용 상수
    const val BLACK_NUMBER = 1
    const val WHITE_NUMBER = 2

    const val ID = "id"
    const val TURN = "turn"
    const val LAST_POSITION = "lastPosition"
    const val BOARD = "board"
    const val USERID = "userId"

    override val name: String = "stone"
    override val scheme: List<Column> = listOf(
        Column(ID, "INTEGER PRIMARY KEY AUTOINCREMENT"),
        Column(TURN, "int"),
        Column(LAST_POSITION, "int"),
        Column(BOARD, "TEXT"),
        Column(USERID, "int")
    )
}
