package woowacourse.omok.dao

import android.database.sqlite.SQLiteOpenHelper

class FakeOmokPlaceDaoImpl(dbHelper: SQLiteOpenHelper) : SimpleOmokPlaceDao(dbHelper) {
    override val boardColumn: String
        get() = "omok_board"
    override val nicknameColumn: String
        get() = "nickname"
    override val tableName: String
        get() = "test"
}
