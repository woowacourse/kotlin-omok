package woowacourse.omok.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class OmokDBHelper(
    context: Context
) : SQLiteOpenHelper(context, DBInfo.DB_NAME, null, DBInfo.DB_VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {
        createPlayerTable(db)
        createBoardTable(db)
    }

    private fun createPlayerTable(db: SQLiteDatabase?) {
        db?.execSQL(
            """
            create table ${PlayerContract.TABLE_NAME} (
            ${PlayerContract.COLUMN_NICKNAME} varchar(4) primary key
            );
            """.trimIndent()
        )
    }

    private fun createBoardTable(db: SQLiteDatabase?) {
        db?.execSQL(
            """
            create table ${BoardContract.TABLE_NAME} (
            ${BoardContract.COLUMN_NICKNAME} varchar(4),
            ${BoardContract.COLUMN_POSITION} int,
            ${BoardContract.COLUMN_STONE} int not null,
            primary key(${BoardContract.COLUMN_NICKNAME}, ${BoardContract.COLUMN_POSITION}),
            foreign key (${BoardContract.COLUMN_NICKNAME}) 
            references ${PlayerContract.TABLE_NAME} (${PlayerContract.COLUMN_NICKNAME}) 
            on delete cascade
            );
            """.trimIndent()
        )
    }

    override fun onOpen(db: SQLiteDatabase?) {
        super.onOpen(db)
        db?.execSQL("PRAGMA foreign_keys = ON;")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        dropPlayerTable(db)
        dropBoardTable(db)
        onCreate(db)
    }

    private fun dropPlayerTable(db: SQLiteDatabase?) {
        db?.execSQL("drop table if exists ${PlayerContract.TABLE_NAME}")
    }

    private fun dropBoardTable(db: SQLiteDatabase?) {
        db?.execSQL("DROP TABLE IF EXISTS ${BoardContract.TABLE_NAME}")
    }
}
