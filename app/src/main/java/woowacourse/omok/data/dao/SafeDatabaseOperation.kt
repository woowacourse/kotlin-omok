package woowacourse.omok.data.dao

import android.database.sqlite.SQLiteDatabase
import woowacourse.omok.data.OmokDatabaseHelper

inline fun <T> OmokDatabaseHelper.safeDatabaseOperation(operation: (db: SQLiteDatabase) -> T): Result<T> =
    try {
        this.writableDatabase.use { db ->
            Result.success(operation(db))
        }
    } catch (e: Exception) {
        Result.failure(e)
    }
