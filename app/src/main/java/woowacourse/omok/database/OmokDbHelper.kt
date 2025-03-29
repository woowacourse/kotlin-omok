package woowacourse.omok.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import woowacourse.omok.model.Color
import woowacourse.omok.model.Stone
import woowacourse.omok.model.position.Col
import woowacourse.omok.model.position.Position
import woowacourse.omok.model.position.Row

class OmokDbHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(OmokContract.SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(
        db: SQLiteDatabase,
        oldVersion: Int,
        newVersion: Int,
    ) {
        db.execSQL(OmokContract.SQL_DELETE_ENTRIES)
        onCreate(db)
    }

    override fun onDowngrade(
        db: SQLiteDatabase,
        oldVersion: Int,
        newVersion: Int,
    ) {
        onUpgrade(db, oldVersion, newVersion)
    }

    fun insertData(
        x: Col,
        y: Row,
        color: Color,
    ) {
        val db = writableDatabase
        val values =
            ContentValues().apply {
                put(OmokContract.COLUMN_NAME_X, x.value)
                put(OmokContract.COLUMN_NAME_Y, y.value)
                put(OmokContract.COLUMN_NAME_COLOR, color.name)
            }
        db.insert(OmokContract.TABLE_NAME, null, values)
    }

    fun clear() {
        writableDatabase.delete(OmokContract.TABLE_NAME, null, null)
    }

    fun queryAll(): List<Stone> {
        val dbReader = readableDatabase
        val result = mutableListOf<Stone>()

        val cursor: Cursor = dbReader.rawQuery("SELECT * FROM ${OmokContract.TABLE_NAME}", null)
        with(cursor) {
            while (moveToNext()) {
                val x: Int = getInt(getColumnIndexOrThrow(OmokContract.COLUMN_NAME_X))
                val y: Int = getInt(getColumnIndexOrThrow(OmokContract.COLUMN_NAME_Y))
                val color: Color =
                    when (getString(getColumnIndexOrThrow(OmokContract.COLUMN_NAME_COLOR))) {
                        Color.BLACK.name -> Color.BLACK
                        Color.WHITE.name -> Color.WHITE
                        else -> throw IllegalStateException()
                    }
                result.add(Stone(Position(Col(x), Row(y)), color))
            }
        }
        cursor.close()
        return result
    }

    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "Omok.db"
    }
}
