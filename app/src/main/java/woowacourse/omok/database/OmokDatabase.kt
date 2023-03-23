package woowacourse.omok.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase

object OmokDatabase {
    fun getInstance(context: Context): SQLiteDatabase =
        OmokDBHelper(context).writableDatabase
}
