package woowacourse.omok.dao

import android.content.ContentValues
import android.database.sqlite.SQLiteAbortException
import android.database.sqlite.SQLiteOpenHelper
import omok.domain.place.Place
import omok.view.ext.serialize
import omok.view.ext.toPlace
import woowacourse.omok.entity.LatestStoneEntity
import woowacourse.omok.entity.OmokBoardEntity

abstract class OmokDao(dbHelper: SQLiteOpenHelper) : Dao {
    private val db = dbHelper.writableDatabase

    override fun insertBoard(item: LatestStoneEntity): Long {
        db.beginTransaction()
        return runCatching {
            val values =
                ContentValues().apply {
                    put(nicknameColumn, item.nickname)
                    put(boardColumn, item.latestStone.serialize())
                }
            db.insert(tableName, null, values)
            updateBoard(item).toLong()
        }.getOrNull()
            ?:
            run {
                db.endTransaction()
                throw SQLiteAbortException(ERR_TRANSACTION)
            }
    }

    override fun updateBoard(item: LatestStoneEntity): Int {
        val values =
            ContentValues().apply {
                put(nicknameColumn, item.nickname)
                put(latestStoneColumn, item.latestStone.serialize())
            }
        val whereClause = "$nicknameColumn = ?"
        val whereArgs = arrayOf(item.nickname.toString())
        val newRowId = db.update(latestStoneTableName, values, whereClause, whereArgs)
        if (newRowId == 0) db.insert(latestStoneTableName, null, values)
        return newRowId
    }

    override fun findBoardByNickName(nickname: String): OmokBoardEntity? {
        val cursor =
            db.query(
                tableName,
                arrayOf(idColumn, nicknameColumn, boardColumn),
                "$nicknameColumn = ?",
                arrayOf(nickname),
                null,
                null,
                null,
            )
        return cursor.use {
            if (it.moveToFirst()) {
                val id = it.getInt(it.getColumnIndexOrThrow(idColumn))
                val nickname = it.getString(it.getColumnIndexOrThrow(nicknameColumn))
                val placeList = mutableListOf<Place>()
                do {
                    placeList.add(it.getString(it.getColumnIndexOrThrow(boardColumn)).toPlace())
                } while (it.moveToNext())
                OmokBoardEntity(id, nickname, placeList.toList())
            } else {
                null
            }
        }
    }

    override fun findLatestStoneByNickName(nickname: String): LatestStoneEntity? {
        val cursor =
            db.query(
                latestStoneTableName,
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

    abstract val latestStoneTableName: String
    abstract val latestStoneColumn: String
    abstract val tableName: String
    abstract val nicknameColumn: String
    abstract val boardColumn: String
    val idColumn: String = "id"

    companion object {
        const val ERR_TRANSACTION = "쿼리에 실패하였습니다"
    }
}
