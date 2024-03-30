package woowacourse.omok.model.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class OmokDbHelper(context: Context) : SQLiteOpenHelper(context, "omok.db", null, 1) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            """CREATE TABLE ${OmokContract.TABLE_NAME} (
                    ${OmokContract.COLUMN_ROW} int not null,
                    ${OmokContract.COLUMN_COL} int not null,
                    ${OmokContract.COLUMN_STONE} varchar(50),
                    ${OmokContract.COLUMN_ID} int not null
                )
                """,
        )
    }

    override fun onUpgrade(
        db: SQLiteDatabase,
        oldVersion: Int,
        newVersion: Int,
    ) {
        TODO("Not yet implemented")
    }
}
