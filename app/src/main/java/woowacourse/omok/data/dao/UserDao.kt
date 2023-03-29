package woowacourse.omok.data.dao

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.provider.BaseColumns
import woowacourse.omok.data.OmokContract.User.CREATE_USER_TABLE
import woowacourse.omok.data.OmokContract.User.DELETE_USER_TABLE
import woowacourse.omok.data.OmokContract.User.TABLE_COLUMN_LOSE
import woowacourse.omok.data.OmokContract.User.TABLE_COLUMN_USER_NAME
import woowacourse.omok.data.OmokContract.User.TABLE_COLUMN_WIN
import woowacourse.omok.data.OmokContract.User.TABLE_NAME
import woowacourse.omok.data.OmokDbHelper
import woowacourse.omok.data.entity.UserEntity

class UserDao(private val context: Context) {
    private val omokDb by lazy { OmokDbHelper(context).readableDatabase }

    fun insertUser(name: String): Int {
        val data = ContentValues()
        data.put(TABLE_COLUMN_USER_NAME, name)
        data.put(TABLE_COLUMN_WIN, 0)
        data.put(TABLE_COLUMN_LOSE, 0)
        return omokDb.insert(TABLE_NAME, null, data).toInt()
    }

    fun getUser(id: Int): UserEntity {
        val selection = "${BaseColumns._ID} == ?"
        val selectionArgs = arrayOf("$id")
        val cursor = makeCursor(selection, selectionArgs, null)
        val user = readUser(cursor) ?: throw IllegalStateException("찾으려는 유저가 없습니다.")
        cursor.close()
        return user
    }

    fun getUsers(): List<UserEntity> {
        val orderBy = "${BaseColumns._ID} ASC"
        val cursor = makeCursor(null, null, orderBy)
        val users = readUsers(cursor)
        cursor.close()
        return users
    }

    private fun readUser(cursor: Cursor): UserEntity? {
        if (!cursor.moveToNext()) return null
        val id = cursor.getInt(cursor.getColumnIndexOrThrow(BaseColumns._ID))
        val name = cursor.getString(cursor.getColumnIndexOrThrow(TABLE_COLUMN_USER_NAME))
        val winCount = cursor.getInt(cursor.getColumnIndexOrThrow(TABLE_COLUMN_WIN))
        val loseCount = cursor.getInt(cursor.getColumnIndexOrThrow(TABLE_COLUMN_LOSE))
        return UserEntity(id, name, winCount, loseCount)
    }

    private fun readUsers(cursor: Cursor): List<UserEntity> {
        val users = mutableListOf<UserEntity>()
        while (true) {
            val user = readUser(cursor) ?: break
            users.add(user)
        }
        return users
    }

    private fun makeCursor(
        selection: String?,
        selectionArgs: Array<String>?,
        orderBy: String?,
    ): Cursor {
        return omokDb.query(
            TABLE_NAME,
            arrayOf(BaseColumns._ID, TABLE_COLUMN_USER_NAME, TABLE_COLUMN_WIN, TABLE_COLUMN_LOSE),
            selection,
            selectionArgs,
            null,
            null,
            orderBy,
        )
    }

    fun closeDb() {
        omokDb.close()
    }

    fun clear() {
        omokDb.execSQL(DELETE_USER_TABLE)
        omokDb.execSQL(CREATE_USER_TABLE)
    }
}
