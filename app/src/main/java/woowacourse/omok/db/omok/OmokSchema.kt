package woowacourse.omok.db.omok

import woowacourse.omok.db.room.RoomSchema

object OmokSchema {
    const val SQL_CREATE_OMOK_TABLE =
        "CREATE TABLE ${OmokContract.TABLE_NAME} (" +
            " ${OmokContract.COLUMN_NAME_BOARD_COLUMN} INTEGER NOT NULL," +
            " ${OmokContract.COLUMN_NAME_BOARD_ROW} INTEGER NOT NULL," +
            " ${OmokContract.COLUMN_NAME_STONE} TEXT," +
            " ${OmokContract.COLUMN_NAME_ROOM_ID} INTEGER NOT NULL," +
            " FOREIGN KEY(${OmokContract.COLUMN_NAME_ROOM_ID}) " +
            " REFERENCES ${RoomSchema.RoomsContract.ROOM_TABLE_NAME}(${RoomSchema.RoomsContract.COLUMN_NAME_ROOM_ID}) " +
            " ON DELETE CASCADE," +
            " UNIQUE(${OmokContract.COLUMN_NAME_BOARD_COLUMN}, ${OmokContract.COLUMN_NAME_BOARD_ROW}) ON CONFLICT IGNORE)"

    const val SQL_DROP_OMOK_TABLE = "DROP TABLE IF EXISTS ${OmokContract.TABLE_NAME}"

    const val SQL_DELETE_OMOK_TABLE = "DELETE FROM ${OmokContract.TABLE_NAME}"

    object OmokContract {
        const val TABLE_NAME = "omok"
        const val COLUMN_NAME_ROOM_ID = "room_id"
        const val COLUMN_NAME_BOARD_COLUMN = "board_column"
        const val COLUMN_NAME_BOARD_ROW = "board_row"
        const val COLUMN_NAME_STONE = "stone"
    }
}
