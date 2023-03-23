package woowacourse.omok

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class BoardDBHelper(context: Context?) :
    SQLiteOpenHelper(context, BoardContract.DATABASE_NAME, null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(
            "CREATE TABLE ${BoardContract.TABLE_NAME}(" +
                " _id int primary key," +
                " ${BoardContract.TABLE_COLUMN_POSITION_INDEX} int, " +
                " ${BoardContract.TABLE_COLUMN_STONE} int" +
                ");"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db?.execSQL("DROP TABLE IF EXISTS ${BoardContract.TABLE_NAME}")
        onCreate(db)
    }
}
