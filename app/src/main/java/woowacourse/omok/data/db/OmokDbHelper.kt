package woowacourse.omok.data.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class OmokDbHelper(context: Context, private val tables: List<SQLiteTable>) :
    SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {
        tables.forEach {
            val columns = it.scheme.joinToString(",") { scheme -> "${scheme.name} ${scheme.type}" }
            db?.execSQL("CREATE TABLE ${it.name} ($columns)")
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        tables.forEach {
            db?.execSQL("DROP TABLE IF EXISTS ${it.name}")
        }
        onCreate(db)
    }

    companion object {
        const val DB_VERSION = 1
        const val DB_NAME = "OMOK"
    }
}
