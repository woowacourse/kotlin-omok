package woowacourse.omok.data.db.omok

import woowacourse.omok.data.db.omok.OmokSchema.OmokContract.COLUMN_NAME_BOARD_COLUMN
import woowacourse.omok.data.db.omok.OmokSchema.OmokContract.COLUMN_NAME_BOARD_ROW
import woowacourse.omok.data.db.omok.OmokSchema.OmokContract.COLUMN_NAME_STONE
import woowacourse.omok.data.db.omok.OmokSchema.OmokContract.TABLE_NAME

object OmokSchema {
    const val SQL_CREATE_OMOK_TABLE =
        "CREATE TABLE $TABLE_NAME (" +
            " $COLUMN_NAME_BOARD_COLUMN INTEGER NOT NULL," +
            " $COLUMN_NAME_BOARD_ROW INTEGER NOT NULL," +
            " $COLUMN_NAME_STONE TEXT," +
//                "FOREIGN KEY(${OmokContract.COLUMN_NAME_ROOM_ID}) " +
//                "REFERENCES ${RoomSchema.RoomsContract.TABLE_NAME}" +
//                "(${RoomSchema.RoomsContract.COLUMN_NAME_ROOM_ID}) ON DELETE CASCADE," +
            " UNIQUE($COLUMN_NAME_BOARD_COLUMN, $COLUMN_NAME_BOARD_ROW) ON CONFLICT IGNORE)"

    const val SQL_DELETE_OMOK_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"

    object OmokContract {
        const val TABLE_NAME = "omok"
        const val COLUMN_NAME_ROOM_ID = "room_id"
        const val COLUMN_NAME_BOARD_COLUMN = "board_column"
        const val COLUMN_NAME_BOARD_ROW = "board_row"
        const val COLUMN_NAME_STONE = "stone"
    }
}
