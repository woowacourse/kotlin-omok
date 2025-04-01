package woowacourse.omok.dao

import android.content.ContentValues
import android.database.sqlite.SQLiteOpenHelper
import omok.view.ext.serialize
import omok.view.ext.toPlace
import woowacourse.omok.entity.LatestStoneEntity

abstract class SimpleLatestStoneDao(dbHelper: SQLiteOpenHelper) : LatestStoneDao {
    private val db = dbHelper.writableDatabase

    override fun updateBoard(item: LatestStoneEntity): Int {
        val values =
            ContentValues().apply {
                put(nicknameColumn, item.nickname)
                put(latestStoneColumn, item.latestStone.serialize())
            }
        val whereClause = "$nicknameColumn = ?"
        val whereArgs = arrayOf(item.nickname.toString())
        val newRowId = db.update(tableName, values, whereClause, whereArgs)
        if (newRowId == 0) db.insert(tableName, null, values)
        return newRowId
    }

    override fun findLatestStoneByNickName(nickname: String): LatestStoneEntity? {
        val cursor =
            db.query(
                tableName,
                arrayOf(idColumn, nicknameColumn, latestStoneColumn),
                "$nicknameColumn = ?",
                arrayOf(nickname),
                null,
                null,
                null,
            )
        return cursor.use {
            if (it.moveToFirst()) {
                LatestStoneEntity(
                    it.getInt(it.getColumnIndexOrThrow(idColumn)),
                    it.getString(it.getColumnIndexOrThrow(nicknameColumn)),
                    it.getString(it.getColumnIndexOrThrow(latestStoneColumn)).toPlace(),
                )
            } else {
                null
            }
        }
    }

    abstract val tableName: String
    abstract val latestStoneColumn: String
    abstract val nicknameColumn: String
    val idColumn: String = "id"
}
