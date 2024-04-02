package woowacourse.omok.local.db

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.core.content.contentValuesOf
import woowacourse.omok.local.model.StoneEntity

class StoneDaoImpl(context: Context) : StoneDao {
    private val omokDbHelper = OmokDbHelper(context)

    override fun save(stoneEntity: StoneEntity): StoneEntity {
        val db = omokDbHelper.writableDatabase
        val values =
            contentValuesOf(
                OmokContract.COORDINATE_X to stoneEntity.x,
                OmokContract.COORDINATE_Y to stoneEntity.y,
            )
        val id = db.insert(OmokContract.TABLE_NAME, null, values)
        return stoneEntity.copy(id = id)
    }

    override fun findAll(): List<StoneEntity> {
        val db = omokDbHelper.readableDatabase
        return db.query(
            OmokContract.TABLE_NAME,
            arrayOf(
                OmokContract.COLUMN_ID,
                OmokContract.COORDINATE_X,
                OmokContract.COORDINATE_Y,
            ),
        ).use { cursor ->
            generateSequence { if (cursor.moveToNext()) cursor else null }
                .map {
                    val id = it.getLong(it.getColumnIndexOrThrow(OmokContract.COLUMN_ID))
                    val x = it.getInt(it.getColumnIndexOrThrow(OmokContract.COORDINATE_X))
                    val y = it.getInt(it.getColumnIndexOrThrow(OmokContract.COORDINATE_Y))
                    StoneEntity(id, x, y)
                }.toList()
        }
    }

    override fun drop() {
        val db = omokDbHelper.writableDatabase
        db.delete(OmokContract.TABLE_NAME, null, null)
    }

    private fun SQLiteDatabase.query(
        table: String,
        columns: Array<String>,
    ): Cursor {
        return query(table, columns, null, null, null, null, null)
    }
}
