package woowacourse.omok.db.room

object RoomSchema {
    const val SQL_CREATE_ROOMS_TABLE =
        "CREATE TABLE ${RoomsContract.ROOM_TABLE_NAME} (" +
            " ${RoomsContract.COLUMN_NAME_ROOM_ID} INTEGER PRIMARY KEY," +
            " ${RoomsContract.COLUMN_NAME_ROOM_NAME} TEXT NOT NULL)"

    const val SQL_DROP_ROOMS_TABLE = "DROP TABLE IF EXISTS ${RoomsContract.ROOM_TABLE_NAME}"

    const val SQL_DELETE_ROOMS_TABLE = "DELETE FROM ${RoomsContract.ROOM_TABLE_NAME}"

    object RoomsContract {
        const val ROOM_TABLE_NAME = "rooms"
        const val COLUMN_NAME_ROOM_ID = "room_id"
        const val COLUMN_NAME_ROOM_NAME = "room_name"
    }
}
