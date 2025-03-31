package woowacourse.omok.fake

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import woowacourse.omok.db.omok.OmokSchema.SQL_CREATE_OMOK_TABLE
import woowacourse.omok.db.omok.OmokSchema.SQL_DROP_OMOK_TABLE
import woowacourse.omok.db.room.RoomSchema.SQL_CREATE_ROOMS_TABLE
import woowacourse.omok.db.room.RoomSchema.SQL_DROP_ROOMS_TABLE

class FakeOmokSQLiteHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onConfigure(db: SQLiteDatabase?) {
        super.onConfigure(db)
        db?.setForeignKeyConstraintsEnabled(true)
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SQL_CREATE_OMOK_TABLE)
        db?.execSQL(SQL_CREATE_ROOMS_TABLE)
    }

    override fun onUpgrade(
        db: SQLiteDatabase?,
        oldVersion: Int,
        newVersion: Int,
    ) {
        db?.execSQL(SQL_DROP_OMOK_TABLE)
        db?.execSQL(SQL_DROP_ROOMS_TABLE)
        onCreate(db)
    }

    companion object {
        private const val DATABASE_NAME = "fakeOmok.db"
        private const val DATABASE_VERSION = 1
    }
}
