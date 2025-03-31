package woowacourse.omok

import android.provider.BaseColumns

object RoomContract {
    const val ROOM_TABLE_NAME = "rooms"
    const val STONE_TABLE_NAME = "stones"
    const val NICKNAME_TABLE_NAME = "nicknames"

    // Rooms table columns
    const val COLUMN_ROOM_ID = BaseColumns._ID
    const val COLUMN_ROOM_NICKNAME_ID = "nickname_id"
    const val COLUMN_ROOM_STONE_COUNT = "stone_count"

    // Stones table columns
    const val COLUMN_STONE_ID = BaseColumns._ID
    const val COLUMN_STONE_ROOM_ID = "room_id"
    const val COLUMN_STONE_X = "x"
    const val COLUMN_STONE_Y = "y"
    const val COLUMN_STONE_COLOR = "color"
    const val COLUMN_STONE_TURN = "turn"

    // Nicknames table columns
    const val COLUMN_NICKNAME_ID = BaseColumns._ID
    const val COLUMN_NICKNAME_NAME = "name"

    // Nickname table
    const val SQL_CREATE_NICKNAMES =
        "CREATE TABLE $NICKNAME_TABLE_NAME (" +
                "$COLUMN_NICKNAME_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$COLUMN_NICKNAME_NAME TEXT NOT NULL)"

    // Room table
    const val SQL_CREATE_ROOMS =
        "CREATE TABLE $ROOM_TABLE_NAME (" +
                "$COLUMN_ROOM_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$COLUMN_ROOM_NICKNAME_ID INTEGER NOT NULL," +
                "$COLUMN_ROOM_STONE_COUNT INTEGER," +
                "FOREIGN KEY($COLUMN_ROOM_NICKNAME_ID) REFERENCES $NICKNAME_TABLE_NAME($COLUMN_NICKNAME_ID) ON DELETE CASCADE)"

    // Stone table
    const val SQL_CREATE_STONES =
        "CREATE TABLE $STONE_TABLE_NAME (" +
                "$COLUMN_STONE_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$COLUMN_STONE_ROOM_ID INTEGER NOT NULL," +
                "$COLUMN_STONE_X INTEGER NOT NULL," +
                "$COLUMN_STONE_Y INTEGER NOT NULL," +
                "$COLUMN_STONE_COLOR TEXT NOT NULL," +
                "$COLUMN_STONE_TURN INTEGER NOT NULL," +
                "FOREIGN KEY($COLUMN_STONE_ROOM_ID) REFERENCES $ROOM_TABLE_NAME($COLUMN_ROOM_ID) ON DELETE CASCADE)"

    const val SQL_DELETE_NICKNAMES = "DROP TABLE IF EXISTS $NICKNAME_TABLE_NAME"
    const val SQL_DELETE_ROOMS = "DROP TABLE IF EXISTS $ROOM_TABLE_NAME"
    const val SQL_DELETE_STONES = "DROP TABLE IF EXISTS $STONE_TABLE_NAME"

    const val SQL_FIND_ROOM_STONES = "SELECT x, y, color FROM stones WHERE room_id = ? ORDER BY turn ASC"
    const val SQL_FIND_ROOMS =
        """
            SELECT r._id, n.name AS nickname, r.stone_count
            FROM rooms r
            JOIN nicknames n ON r.nickname_id = n._id
            WHERE n.name = ?
        """

    const val SQL_FIND_ROOMS_NICKNAME = "nickname"

    const val SQL_FIND_ROOM_USER_ID = "SELECT _id FROM nicknames WHERE name = ?"
}




