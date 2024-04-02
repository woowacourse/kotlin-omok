package woowacourse.omok

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class OmokDbHelper(context: Context, private val tableName: String, private val sql: String) : SQLiteOpenHelper(
    context,
    "$tableName.db",
    null,
    1,
) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(sql)
    }

    override fun onUpgrade(
        db: SQLiteDatabase,
        oldVersion: Int,
        newVersion: Int,
    ) {
        val sql = "DROP TABLE if exists Omok"

        db.execSQL(sql)
        onCreate(db)
    }
}
