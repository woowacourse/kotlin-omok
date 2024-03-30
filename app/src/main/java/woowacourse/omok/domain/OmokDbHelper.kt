package woowacourse.omok.domain

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class OmokDbHelper(
    context: Context,
) : SQLiteOpenHelper(context, "alsong.db", null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = """
            create table notation (
                color varchar(30) not null,
                rowCoordinate int,   
                colCoordinate int
            )
        """
        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(
        db: SQLiteDatabase?,
        oldVersion: Int,
        newVersion: Int,
    ) {
        db?.execSQL("DROP TABLE IF EXISTS notation")
        onCreate(db)
    }
}
