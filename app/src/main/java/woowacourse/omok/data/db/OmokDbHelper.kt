package woowacourse.omok.data.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import woowacourse.omok.data.db.table.UserTable

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

    fun selectUserByName(name: String): User? {
        val cursor = readableDatabase.rawQuery(
            "SELECT * FROM ${UserTable.name}  WHERE ${UserTable.NAME} = $name",
            null
        )
        val user = mutableListOf<User>()
        with(cursor) {
            while (moveToNext()) {
                user.add(
                    User(
                        getInt(getColumnIndexOrThrow(UserTable.ID)),
                        getString(getColumnIndexOrThrow(UserTable.NAME))
                    )
                )
            }
        }
        cursor.close()
        return user.firstOrNull()
    }

    fun insertUser(name: String) {
        val contentValues = ContentValues().apply {
            put(UserTable.NAME, name)
        }
        writableDatabase.insert(UserTable.name, null, contentValues)
    }

    companion object {
        const val DB_VERSION = 1
        const val DB_NAME = "OMOK"
    }
}
