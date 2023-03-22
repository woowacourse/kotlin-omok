package woowacourse.omok.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class PlayerDBHelper(
    context: Context?
) : SQLiteOpenHelper(context, DBInfo.DB_NAME, null, DBInfo.DB_VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(
            """
            create table ${PlayerContract.TABLE_NAME} (
            ${PlayerContract.COLUMN_NICKNAME} varchar(4) primary key
            );
            """.trimIndent()
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("drop table if exists ${PlayerContract.TABLE_NAME}")
        onCreate(db)
    }
}
