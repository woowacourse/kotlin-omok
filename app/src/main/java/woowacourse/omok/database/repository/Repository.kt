package woowacourse.omok.database.repository

import android.content.ContentValues
import android.database.Cursor

interface Repository {
    fun getAll(action: (Cursor) -> Unit)
    fun insert(record: ContentValues)
    fun isEmpty(): Boolean
    fun clear()
    fun close()
}
