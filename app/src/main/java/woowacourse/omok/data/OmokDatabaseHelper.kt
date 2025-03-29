package woowacourse.omok.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class OmokDatabaseHelper(context: Context) : SQLiteOpenHelper(context, "omok_db", null, 1) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            """
            CREATE TABLE stones (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                x INTEGER NOT NULL,
                y INTEGER NOT NULL,
                stone_type TEXT NOT NULL
            );
            """.trimIndent(),
        )
    }

    override fun onUpgrade(
        db: SQLiteDatabase,
        oldVersion: Int,
        newVersion: Int,
    ) {
        db.execSQL("DROP TABLE IF EXISTS stones")
        onCreate(db)
    }

    override fun onDowngrade(
        db: SQLiteDatabase,
        oldVersion: Int,
        newVersion: Int,
    ) {
        onUpgrade(db, oldVersion, newVersion)
    }
}
