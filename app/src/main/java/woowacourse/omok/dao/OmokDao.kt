package woowacourse.omok.dao

import android.content.ContentValues
import android.database.sqlite.SQLiteOpenHelper
import woowacourse.omok.entity.OmokBoardEntity

abstract class OmokDao(dbHelper: SQLiteOpenHelper) : Dao {
    private val db = dbHelper.writableDatabase

    override fun updateBoard(item: OmokBoardEntity): Int {
        val values =
            ContentValues().apply {
                put(nicknameColumn, item.nickname)
                put(boardColumn, item.board)
            }
        val selection = "$nicknameColumn = ?"
        val args = arrayOf(item.nickname)
        val newRowId = db.update(tableName, values, selection, args)

        if (newRowId == 0) insertBoard(item)
        return newRowId
    }

    override fun insertBoard(item: OmokBoardEntity): Long {
        val values =
            ContentValues().apply {
                put(nicknameColumn, item.nickname)
                put(boardColumn, item.board)
            }
        val newRowId = db.insert(tableName, null, values)
        return newRowId
    }

    override fun findBoardByNickName(nickname: String): OmokBoardEntity? {
        val cursor =
            db.query(
                tableName,
                arrayOf(nicknameColumn, boardColumn),
                "$nicknameColumn = ?",
                arrayOf(nickname),
                null,
                null,
                null,
            )
        var item: OmokBoardEntity? = null
        if (cursor.moveToFirst()) {
            item =
                OmokBoardEntity(
                    cursor.getString(cursor.getColumnIndexOrThrow(nicknameColumn)),
                    cursor.getString(cursor.getColumnIndexOrThrow(boardColumn)),
                )
        }
        cursor.close()
        return item
    }

    abstract val tableName: String
    abstract val nicknameColumn: String
    abstract val boardColumn: String
}
