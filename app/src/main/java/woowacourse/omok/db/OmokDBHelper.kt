package woowacourse.omok.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class OmokDBHelper(
    context: Context,
) : SQLiteOpenHelper(context, "game.db", null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(
            "CREATE TABLE ${OmokConstant.TABLE_NAME} (" +
                "${OmokConstant.TABLE_COLUMN_X} int not null," +
                "${OmokConstant.TABLE_COLUMN_Y} int not null," +
                "${OmokConstant.TABLE_COLUMN_COLOR} int not null" +
                ");"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db?.execSQL("DROP TABLE IF EXISTS ${OmokConstant.TABLE_NAME};")
        onCreate(db)
    }
}
