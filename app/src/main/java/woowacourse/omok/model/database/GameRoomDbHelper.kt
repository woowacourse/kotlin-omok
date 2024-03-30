package woowacourse.omok.model.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns

class GameRoomDbHelper(
    context: Context,
) : SQLiteOpenHelper(context, GameRoomContract.TABLE_NAME, null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(
            """
                CREATE TABLE ${GameRoomContract.TABLE_NAME} (
                  ${BaseColumns._ID} int PRIMARY KEY AUTOINCREMENT not null,
                  ${GameRoomContract.COLUMN_TITLE} varchar(30) not null,
                  ${GameRoomContract.COLUMN_STATUS} int
                )
            """,
        )
    }

    override fun onUpgrade(
        db: SQLiteDatabase?,
        oldVersion: Int,
        newVersion: Int,
    ) {
    }
}
