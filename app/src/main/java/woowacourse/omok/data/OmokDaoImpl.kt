package woowacourse.omok.data

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.core.content.contentValuesOf

class OmokDaoImpl(context: Context) : OmokDao {
    private val omokDbHelper = OmokDbHelper(context)

    override fun save(omokEntity: OmokEntity): OmokEntity {
        val db = omokDbHelper.writableDatabase
        val id =
            db.insert(
                OmokContract.TABLE_NAME,
                null,
                contentValuesOf(
                    OmokContract.COLUMN_ROW to omokEntity.row,
                    OmokContract.COLUMN_COL to omokEntity.col,
                    OmokContract.COLUMN_STONE to omokEntity.stone,
                ),
            )
        return omokEntity.copy(id = id)
    }

    override fun findAll(): List<OmokEntity> {
        val db = omokDbHelper.readableDatabase
        val cursor =
            db.query(
                OmokContract.TABLE_NAME,
                arrayOf(
                    OmokContract.COLUMN_ID,
                    OmokContract.COLUMN_ROW,
                    OmokContract.COLUMN_COL,
                    OmokContract.COLUMN_STONE,
                ),
            )

        val result = mutableListOf<OmokEntity>()
        while (cursor.moveToNext()) {
            val id = cursor.getLong(cursor.getColumnIndexOrThrow(OmokContract.COLUMN_ID))
            val row = cursor.getInt(cursor.getColumnIndexOrThrow(OmokContract.COLUMN_ROW))
            val col = cursor.getInt(cursor.getColumnIndexOrThrow(OmokContract.COLUMN_COL))
            val stone = cursor.getString(cursor.getColumnIndexOrThrow(OmokContract.COLUMN_STONE))

            val omokEntity = OmokEntity(row, col, stone, id)
            result.add(omokEntity)
        }

        cursor.close()
        return result
    }

    override fun findLast(): OmokEntity? {
        return findAll().maxByOrNull { it.id }
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
