package woowacourse.omok.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class OmokDbHelper(
    context: Context,
    dbName: String = DBNAME,
) : SQLiteOpenHelper(context, dbName, null, 1) {
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
                create table ${StoneContract.StoneEntry.TABLE_NAME}(
                ${StoneContract.StoneEntry.COLUMN_NAME_X} integer, 
                ${StoneContract.StoneEntry.COLUMN_NAME_Y} integer,
                ${StoneContract.StoneEntry.COLUMN_NAME_STONECOLOR} integer,
                primary key (${StoneContract.StoneEntry.COLUMN_NAME_X}, ${StoneContract.StoneEntry.COLUMN_NAME_Y})
                )
            """
        private const val SQL_DROP_STONES =
            "drop table if exists ${StoneContract.StoneEntry.TABLE_NAME}"
        private const val DBNAME = "Omok"
    }
}
