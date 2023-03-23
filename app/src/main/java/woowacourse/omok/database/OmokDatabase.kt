package woowacourse.omok.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import woowacourse.omok.database.OmokDBHelper

object OmokDatabase {
    fun getInstance(context: Context): SQLiteDatabase =
        OmokDBHelper(context).writableDatabase
}
