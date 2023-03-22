package woowacourse.omok.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class OmokDBHelper(context: Context, private val tables: List<SQLiteTable>) :
    SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    override fun onCreate(p0: SQLiteDatabase?) {
        tables.forEach {
            val columns = it.scheme.joinToString(",") { scheme -> "${scheme.name} ${scheme.type}" }
            p0?.execSQL("CREATE TABLE ${it.name} (${columns})")
        }
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        tables.forEach {
            p0?.execSQL("DROP TABLE IF EXISTS ${it.name}")
        }
        onCreate(p0)
    }

    companion object {
        const val DB_VERSION = 1
        const val DB_NAME = "OMOK"
    }
}