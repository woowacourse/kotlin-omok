package woowacourse.omok.model.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns

class OmokDbHelper(
    context: Context,
) : SQLiteOpenHelper(context, DB_NAME, null, 2) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(CREATE_GAME_ROOM_TABLE)
        db?.execSQL(CREATE_PLACEMENT_TABLE)
    }

    override fun onUpgrade(
        db: SQLiteDatabase?,
        oldVersion: Int,
        newVersion: Int,
    ) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_GAME_ROOM")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_PLACEMENT")
        onCreate(db)
    }

    companion object {
        private const val DB_NAME = "omok_db"
        private const val TABLE_GAME_ROOM = "table_game_room"
        private const val TABLE_PLACEMENT = "table_placement"

        private const val CREATE_GAME_ROOM_TABLE =
            """
                CREATE TABLE ${GameRoomContract.TABLE_NAME} (
                  ${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT not null,
                  ${GameRoomContract.COLUMN_TITLE} varchar(30) not null
                )
            """

        private const val CREATE_PLACEMENT_TABLE = """
                CREATE TABLE ${PlacementContract.TABLE_NAME} (
                  ${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT not null,
                  ${PlacementContract.COLUMN_ROOM_ID} INTEGER not null,
                  ${PlacementContract.COLUMN_COLOR} varchar(5) not null,
                  ${PlacementContract.COLUMN_PLACEMENT_HORIZONTAL_COORDINATE} int,
                  ${PlacementContract.COLUMN_PLACEMENT_VERTICAL_COORDINATE} int
                )
            """
    }
}
