package woowacourse.omok.dao

import android.database.sqlite.SQLiteOpenHelper

class FakeLatestStoneDaoImpl(dbHelper: SQLiteOpenHelper) : SimpleLatestStoneDao(dbHelper) {
    override val latestStoneColumn: String
        get() = "latest_stone"
    override val nicknameColumn: String
        get() = "nickname"
    override val tableName: String
        get() = "test"
}
