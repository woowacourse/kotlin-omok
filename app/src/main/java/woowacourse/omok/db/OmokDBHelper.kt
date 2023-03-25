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
            CREATE TABLE ${PlayerContract.TABLE_NAME} (
            ${PlayerContract.COLUMN_NICKNAME} VARCHAR(4) PRIMARY KEY
            );
            """.trimIndent()
        )
    }

    private fun createBoardTable(db: SQLiteDatabase?) {
        db?.execSQL(
            """
            CREATE TABLE ${BoardContract.TABLE_NAME} (
            ${BoardContract.COLUMN_NICKNAME} VARCHAR(4),
            ${BoardContract.COLUMN_POSITION} INT,
            ${BoardContract.COLUMN_STONE} INT NOT NULL,
            PRIMARY KEY (${BoardContract.COLUMN_NICKNAME}, ${BoardContract.COLUMN_POSITION}),
            FOREIGN KEY (${BoardContract.COLUMN_NICKNAME}) 
            REFERENCES ${PlayerContract.TABLE_NAME} (${PlayerContract.COLUMN_NICKNAME}) 
            ON DELETE CASCADE
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
        db?.execSQL("DROP TABLE IF EXISTS ${PlayerContract.TABLE_NAME}")
    }

    private fun dropBoardTable(db: SQLiteDatabase?) {
        db?.execSQL("DROP TABLE IF EXISTS ${BoardContract.TABLE_NAME}")
    }
}
