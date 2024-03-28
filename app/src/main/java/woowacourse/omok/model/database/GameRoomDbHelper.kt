package woowacourse.omok.model.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class GameRoomDbHelper(
    context: Context,
) : SQLiteOpenHelper(context, GameRoomContract.TABLE_NAME, null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(
            "CREATE TABLE ${GameRoomContract.TABLE_NAME} (\n" +
                "  ${GameRoomContract.COLUMN_TITLE} varchar(30) not null,\n" +
                "  ${GameRoomContract.COLUMN_STATUS} int\n" +
                ")",
        )
    }

    override fun onUpgrade(
        db: SQLiteDatabase?,
        oldVersion: Int,
        newVersion: Int,
    ) {
    }
}
