package woowacourse.omok.database.user

import android.database.sqlite.SQLiteOpenHelper
import androidx.core.content.contentValuesOf
import woowacourse.omok.database.omokturn.querySelectAll

class UserDao(private val sqLiteOpenHelper: SQLiteOpenHelper) {
    fun createTable() {
        val db = sqLiteOpenHelper.writableDatabase
        db.execSQL(UserContract.CREATE_USER_TABLE)
    }

    fun save(user: User): User {
        val db = sqLiteOpenHelper.writableDatabase
        val id =
            db.insert(
                UserContract.TABLE_NAME,
                null,
                contentValuesOf(
                    UserContract.COLUMN_NAME to user.name,
                    UserContract.COLUMN_WIN to user.winCount,
                    UserContract.COLUMN_LOSE to user.loseCount,
                ),
            )
        return user.copy(id = id)
    }

    fun findAll(): List<User> {
        val db = sqLiteOpenHelper.readableDatabase
        return db.querySelectAll(
            UserContract.TABLE_NAME,
            UserContract.ALL_COLUMNS,
        ).use { cursor ->
            mutableListOf<User>().apply {
                while (cursor.moveToNext()) {
                    val id = cursor.getLong(cursor.getColumnIndexOrThrow(UserContract.COLUMN_ID))
                    val name = cursor.getString(cursor.getColumnIndexOrThrow(UserContract.COLUMN_NAME))
                    val winCount = cursor.getInt(cursor.getColumnIndexOrThrow(UserContract.COLUMN_WIN))
                    val loseCount = cursor.getInt(cursor.getColumnIndexOrThrow(UserContract.COLUMN_LOSE))

                    add(User(name, winCount, loseCount, id))
                }
            }
        }
    }

    fun drop() {
        val db = sqLiteOpenHelper.writableDatabase
        db.execSQL(UserContract.DROP_USER_TABLE)
    }

    fun clearAll() {
        val db = sqLiteOpenHelper.writableDatabase
        db.delete(UserContract.TABLE_NAME, null, null)
    }
}
