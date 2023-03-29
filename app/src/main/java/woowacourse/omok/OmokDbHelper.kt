package woowacourse.omok

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import domain.State
import domain.Stone

class OmokDbHelper(
    context: Context
) : SQLiteOpenHelper(context, DB_NAME, null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        val createQuery = "CREATE TABLE ${OmokConstract.TABLE_NAME} (" +
            "  ${OmokConstract.TABLE_COLUMN_STATE} varchar(30) not null," +
            "  ${OmokConstract.TABLE_COLUMN_ROW} int," +
            "  ${OmokConstract.TABLE_COLUMN_COLUMN} int" +
            ");"

        db?.execSQL(createQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db?.execSQL("DROP TABLE IF EXISTS ${OmokConstract.TABLE_NAME}")
        onCreate(db)
    }

    fun insertData(row: Int, column: Int, state: State) {
        val db = this.writableDatabase
        val data = ContentValues().apply {
            put(OmokConstract.TABLE_COLUMN_STATE, state.name)
            put(OmokConstract.TABLE_COLUMN_ROW, row)
            put(OmokConstract.TABLE_COLUMN_COLUMN, column)
        }
        db.insert(OmokConstract.TABLE_NAME, null, data)
        db.close()
    }

    fun getStones(state: State): List<Stone> {
        val db = this.writableDatabase
        val cursor = db.query(
            OmokConstract.TABLE_NAME,
            arrayOf(OmokConstract.TABLE_COLUMN_ROW, OmokConstract.TABLE_COLUMN_COLUMN),
            "${OmokConstract.TABLE_COLUMN_STATE} = ?",
            arrayOf(state.name),
            null,
            null,
            null
        )
        val stones = mutableListOf<Stone>()
        while (cursor.moveToNext()) {
            stones.add(
                Stone(
                    cursor.getInt(cursor.getColumnIndexOrThrow(OmokConstract.TABLE_COLUMN_ROW)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(OmokConstract.TABLE_COLUMN_COLUMN))
                )
            )
        }
        cursor.close()
        db.close()
        return stones
    }

    fun deleteData() {
        val db = this.writableDatabase
        db.execSQL("DELETE FROM ${OmokConstract.TABLE_NAME}")
    }

    companion object {
        private const val DB_NAME = "krrong_db"
    }
}
