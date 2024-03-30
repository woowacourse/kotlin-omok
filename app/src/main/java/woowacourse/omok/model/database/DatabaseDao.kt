package woowacourse.omok.model.database

import android.database.sqlite.SQLiteOpenHelper

interface DatabaseDao<T> {
    val dbHelper: SQLiteOpenHelper

    fun save(item: T): T

    fun drop()
}
