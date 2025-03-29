package woowacourse.omok.model.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.core.database.sqlite.transaction

class OmokDBHelper(
    context: Context,
) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "Omok.db"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(OmokDBContract.StonesTable.SQL_CREATE_ENTRIES)
        db.execSQL(OmokDBContract.GameRoomsTable.SQL_CREATE_ENTRIES)
        db.execSQL(OmokDBContract.PlayerTable.SQL_CREATE_ENTRIES)
    }

    fun resetDatabase() {
        val db = writableDatabase
        db.execSQL(OmokDBContract.StonesTable.SQL_DELETE_ENTRIES)
        onCreate(db)
    }

    fun roomWithStonesDelete(roomId: Int) {
        val db = writableDatabase
        db.transaction {
            val stonesWhereClause = "${OmokDBContract.StonesTable.COLUMN_ROOM_ID} = ?"
            val stonesWhereArgs = arrayOf(roomId.toString())
            delete(OmokDBContract.StonesTable.TABLE_NAME, stonesWhereClause, stonesWhereArgs)

            val roomWhereClause = "${OmokDBContract.GameRoomsTable.COLUMN_ROOM_ID} = ?"
            val roomWhereArgs = arrayOf(roomId.toString())
            delete(OmokDBContract.GameRoomsTable.TABLE_NAME, roomWhereClause, roomWhereArgs)
        }
    }

    fun resetAllDatabase() {
        val db = writableDatabase
        db.execSQL(OmokDBContract.StonesTable.SQL_DELETE_ENTRIES)
        db.execSQL(OmokDBContract.GameRoomsTable.SQL_DELETE_ENTRIES)
        db.execSQL(OmokDBContract.PlayerTable.SQL_DELETE_ENTRIES)
    }

    override fun onUpgrade(
        db: SQLiteDatabase,
        oldVersion: Int,
        newVersion: Int,
    ) {
        db.execSQL(OmokDBContract.StonesTable.SQL_DELETE_ENTRIES)
        onCreate(db)
    }

    override fun onDowngrade(
        db: SQLiteDatabase,
        oldVersion: Int,
        newVersion: Int,
    ) {
        onUpgrade(db, oldVersion, newVersion)
    }
}
