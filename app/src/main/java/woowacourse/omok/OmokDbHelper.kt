package woowacourse.omok

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class OmokDbHelper(context: Context) : SQLiteOpenHelper(context, "omok.db", null, 1) {
    val tableName = "OMOK"
    val pointRow = "point_row"
    val pointCol = "point_col"
    val color = "color"

    override fun onCreate(db: SQLiteDatabase) {
        val sql =
            "CREATE TABLE IF NOT EXISTS $tableName (\n" +
                "$pointRow int not null,\n" +
                "$pointCol int not null,\n" +
                "$color int not null\n" +
                ")"

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
