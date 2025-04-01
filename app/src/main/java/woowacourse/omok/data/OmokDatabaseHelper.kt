package woowacourse.omok.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class OmokDatabaseHelper(
    private val context: Context,
    private val useInMemory: Boolean = false,
) : SQLiteOpenHelper(context, if (useInMemory) TEST_DB_NAME else DB_NAME, null, DB_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(GamesTableContract.CREATE_TABLE)
        db.execSQL(MovesTableContract.CREATE_TABLE)
    }

    override fun onUpgrade(
        db: SQLiteDatabase,
        oldVersion: Int,
        newVersion: Int,
    ) {
        db.execSQL("DROP TABLE IF EXISTS ${GamesTableContract.TABLE_NAME}")
        db.execSQL("DROP TABLE IF EXISTS ${MovesTableContract.TABLE_NAME}")
        onCreate(db)
    }

    fun deleteDatabase() {
        val targetDb = if (useInMemory) TEST_DB_NAME else DB_NAME
        context.deleteDatabase(targetDb)
    }

    companion object {
        private const val DB_NAME = "OmokGame.db"
        private const val TEST_DB_NAME = "test.db"
        private const val DB_VERSION = 1
    }
}
