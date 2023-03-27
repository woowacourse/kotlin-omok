package woowacourse.omok.data.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase

object OmokDatabase {
    fun getInstance(context: Context): SQLiteDatabase =
        OmokDBHelper(context).writableDatabase
}
