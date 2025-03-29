package woowacourse.omok.db

import android.content.ContentValues
import woowacourse.omok.db.OmokDbHelper.Companion.BOARD_COLUMN
import woowacourse.omok.db.OmokDbHelper.Companion.NICKNAME_COLUMN
import woowacourse.omok.db.OmokDbHelper.Companion.TABLE_NAME
import woowacourse.omok.entity.OmokBoardEntity

class OmokDaoImpl(dbHelper: OmokDbHelper) : OmokDao {
    private val db = dbHelper.writableDatabase

    override fun updateBoard(item: OmokBoardEntity): Int {
        val values =
            ContentValues().apply {
                put(NICKNAME_COLUMN, item.nickname)
                put(BOARD_COLUMN, item.board)
            }
        val selection = "$NICKNAME_COLUMN = ?"
        val args = arrayOf(item.nickname)
        val newRowId = db.update(TABLE_NAME, values, selection, args)

        if (newRowId == 0) insertBoard(item)
        return newRowId
    }

    override fun insertBoard(item: OmokBoardEntity): Long {
        val values =
            ContentValues().apply {
                put(NICKNAME_COLUMN, item.nickname)
                put(BOARD_COLUMN, item.board)
            }
        val newRowId = db.insert(TABLE_NAME, null, values)
        return newRowId
    }

    override fun findBoardByNickName(nickname: String): OmokBoardEntity? {
        val cursor =
            db.query(
                TABLE_NAME,
                arrayOf(NICKNAME_COLUMN, BOARD_COLUMN),
                "$NICKNAME_COLUMN = ?",
                arrayOf(nickname),
                null,
                null,
                null,
            )
        var item: OmokBoardEntity? = null
        if (cursor.moveToFirst()) {
            item =
                OmokBoardEntity(
                    cursor.getString(cursor.getColumnIndexOrThrow(NICKNAME_COLUMN)),
                    cursor.getString(cursor.getColumnIndexOrThrow(BOARD_COLUMN)),
                )
        }
        cursor.close()
        return item
    }
}
