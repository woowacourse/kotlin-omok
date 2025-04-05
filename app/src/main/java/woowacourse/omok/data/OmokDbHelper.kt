package woowacourse.omok.data

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import woowacourse.omok.data.OmokStorageController.Companion.COLUMN_NAME_POSITION
import woowacourse.omok.data.OmokStorageController.Companion.COLUMN_NAME_STONE
import woowacourse.omok.data.OmokStorageController.Companion.TABLE_NAME

class OmokDbHelper(
    context: Context,
) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(
        db: SQLiteDatabase,
        oldVersion: Int,
        newVersion: Int,
    ) {
        db.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }

    override fun onDowngrade(
        db: SQLiteDatabase,
        oldVersion: Int,
        newVersion: Int,
    ) {
        onUpgrade(db, oldVersion, newVersion)
    }

    fun insert(
        tableName: String,
        values: ContentValues,
    ) {
        writableDatabase.insert(tableName, null, values)
    }

    fun load(
        tableName: String,
        columns: Array<String>,
        orderBy: String?,
        limit: String?,
    ): Cursor =
        readableDatabase.query(
            tableName,
            columns,
            null,
            null,
            null,
            null,
            orderBy,
            limit,
        )

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "Omok.db"
        private const val COLUMN_NAME_ID = BaseColumns._ID

        private const val SQL_CREATE_ENTRIES =
            "CREATE TABLE $TABLE_NAME (" +
                "$COLUMN_NAME_ID INTEGER PRIMARY KEY," +
                "$COLUMN_NAME_POSITION TEXT," +
                "$COLUMN_NAME_STONE TEXT)"

        private const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS $TABLE_NAME"
    }
}
