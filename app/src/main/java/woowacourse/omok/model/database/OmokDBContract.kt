package woowacourse.omok.model.database

import java.time.format.DateTimeFormatter

object OmokDBContract {
    val dbTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

    object StonesTable {
        const val TABLE_NAME = "stones"
        const val COLUMN_ROOM_ID = "room_id"
        const val COLUMN_NAME_ORDER = "stone_order"
        const val COLUMN_NAME_ROW_INDEX = "row_index"
        const val COLUMN_NAME_COL_INDEX = "col_index"
        const val COLUMN_NAME_STONE_COLOR = "stone_color"

        const val SQL_CREATE_ENTRIES =
            "CREATE TABLE $TABLE_NAME (" +
                "$COLUMN_NAME_ORDER INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COLUMN_ROOM_ID INTEGER NOT NULL, " +
                "$COLUMN_NAME_ROW_INDEX INTEGER NOT NULL, " +
                "$COLUMN_NAME_COL_INDEX INTEGER NOT NULL, " +
                "$COLUMN_NAME_STONE_COLOR TEXT NOT NULL)"

        const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS $TABLE_NAME"
    }

    object GameRoomsTable {
        const val TABLE_NAME = "game_room"
        const val COLUMN_ROOM_ID = "room_id"
        const val COLUMN_BLACK_PLAYER_NAME = "balck_player_name"
        const val COLUMN_WHITE_PLAYER_NAME = "white_player_name"
        const val COLUMN_LAST_PLAY_TIME = "last_play_time"

        const val SQL_CREATE_ENTRIES =
            "CREATE TABLE $TABLE_NAME (" +
                "$COLUMN_ROOM_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COLUMN_BLACK_PLAYER_NAME TEXT NOT NULL, " +
                "$COLUMN_WHITE_PLAYER_NAME TEXT NOT NULL, " +
                "$COLUMN_LAST_PLAY_TIME TIMESTAMP DEFAULT CURRENT_TIMESTAMP)"

        const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS $TABLE_NAME"
    }

    object PlayerTable {
        const val TABLE_NAME = "player"
        const val COLUMN_PLAYER_NAME = "balck_player_name"
        const val COLUMN_PLAY_COUNT = "black_win_count"
        const val COLUMN_BLACK_WIN_COUNT = "black_win_count"
        const val COLUMN_WHITE_WIN_COUNT = "white_win_count"

        const val SQL_CREATE_ENTRIES =
            "CREATE TABLE $TABLE_NAME (" +
                "$COLUMN_PLAYER_NAME TEXT PRIMARY KEY, " +
                "$COLUMN_PLAY_COUNT INTEGER NOT NULL, " +
                "$COLUMN_BLACK_WIN_COUNT INTEGER NOT NULL, " +
                "$COLUMN_WHITE_WIN_COUNT INTEGER NOT NULL)"

        const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS $TABLE_NAME"
    }
}
