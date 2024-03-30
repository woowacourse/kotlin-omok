package woowacourse.omok.model.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns

class PlacementDbHelper(
    context: Context,
) : SQLiteOpenHelper(context, PlacementContract.TABLE_NAME, null, 3) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(
            """
                CREATE TABLE ${PlacementContract.TABLE_NAME} (
                  ${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT not null,
                  ${PlacementContract.COLUMN_ROOM_ID} INTEGER not null,
                  ${PlacementContract.COLUMN_COLOR} varchar(5) not null,
                  ${PlacementContract.COLUMN_PLACEMENT_INDEX} int
                )
            """,
        )
    }

    override fun onUpgrade(
        db: SQLiteDatabase?,
        oldVersion: Int,
        newVersion: Int,
    ) {
        if (oldVersion <= 2) {
            db?.execSQL("DROP TABLE IF EXISTS ${PlacementContract.TABLE_NAME}")
            onCreate(db)
        }
    }
}
