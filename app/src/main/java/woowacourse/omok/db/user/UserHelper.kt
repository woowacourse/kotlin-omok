package woowacourse.omok.db.user

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import woowacourse.omok.db.db_name
import woowacourse.omok.db.db_version

class UserHelper(
    context: Context?
) : SQLiteOpenHelper(context, db_name, null, db_version) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(
            "CREATE TABLE ${UserConstract.TABLE_NAME}(" +
                "  ${UserConstract.TABLE_COLUMN_NAME} varchar(30) primary key" +
                ");"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS ${UserConstract.TABLE_NAME}")
        onCreate(db)
    }
}
