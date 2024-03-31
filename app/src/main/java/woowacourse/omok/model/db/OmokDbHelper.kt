package woowacourse.omok.model.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns

class OmokDbHelper(context: Context) : SQLiteOpenHelper(context, DBNAME, null, 1) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_STONES)
    }

    override fun onUpgrade(
        db: SQLiteDatabase?,
        oldVersion: Int,
        newVersion: Int,
    ) {
    }

    override fun onDowngrade(
        db: SQLiteDatabase?,
        oldVersion: Int,
        newVersion: Int,
    ) {
        super.onDowngrade(db, oldVersion, newVersion)
    }

    companion object {
        private const val SQL_CREATE_STONES =
            """
                create table ${StoneContract.StoneEntry.TABLE_NAME}
                (${BaseColumns._ID} integer primary key, 
                ${StoneContract.StoneEntry.COLUMN_NAME_X} integer, 
                ${StoneContract.StoneEntry.COLUMN_NAME_Y} integer,
                ${StoneContract.StoneEntry.COLUMN_NAME_STONECOLOR} integer)
            """
        private const val SQL_DROP_STONES = "drop table if exists ${StoneContract.StoneEntry.TABLE_NAME}"
        private const val DBNAME = "Omok"
    }
}
