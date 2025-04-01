package woowacourse.omok.dao

import android.content.ContentValues
import android.database.sqlite.SQLiteAbortException
import android.database.sqlite.SQLiteOpenHelper
import omok.domain.place.Place
import omok.view.ext.serialize
import omok.view.ext.toPlace
import woowacourse.omok.entity.LatestStoneEntity
import woowacourse.omok.entity.OmokBoardEntity

abstract class SimpleOmokPlaceDao(dbHelper: SQLiteOpenHelper) : OmokPlaceDao {
    private val db = dbHelper.writableDatabase

    override fun insertBoard(item: LatestStoneEntity): Long {
        return runCatching {
            val values =
                ContentValues().apply {
                    put(nicknameColumn, item.nickname)
                    put(boardColumn, item.latestStone.serialize())
                }
            db.insert(tableName, null, values)
        }.getOrNull()
            ?: run {
                throw SQLiteAbortException(ERR_TRANSACTION)
            }
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

    abstract val tableName: String
    abstract val nicknameColumn: String
    abstract val boardColumn: String
    val idColumn: String = "id"

    companion object {
        const val ERR_TRANSACTION = "쿼리에 실패하였습니다"
    }
}
