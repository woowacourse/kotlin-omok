package woowacourse.omok.model.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class PlacementDbHelper(
    context: Context,
) : SQLiteOpenHelper(context, PlacementContract.TABLE_NAME, null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(
            """
                CREATE TABLE ${PlacementContract.TABLE_NAME} (
                  ${PlacementContract.COLUMN_ROOM_ID} INTEGER not null,
                  ${PlacementContract.COLUMN_COLOR} varchar(5) not null,
                  ${PlacementContract.COLUMN_HORIZONTAL_COORDINATE} int,
                  ${PlacementContract.COLUMN_VERTICAL_COORDINATE} int
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
