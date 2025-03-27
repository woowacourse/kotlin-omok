package woowacourse.omok.data.db

import woowacourse.omok.data.db.OmokSchema.OmokContract.COLUMN_NAME_BOARD_COLUMN
import woowacourse.omok.data.db.OmokSchema.OmokContract.COLUMN_NAME_BOARD_ROW
import woowacourse.omok.data.db.OmokSchema.OmokContract.COLUMN_NAME_STONE
import woowacourse.omok.data.db.OmokSchema.OmokContract.TABLE_NAME

object OmokSchema {
    const val SQL_CREATE_ENTRIES =
        "CREATE TABLE $TABLE_NAME (" +
            " $COLUMN_NAME_BOARD_COLUMN INTEGER NOT NULL," +
            " $COLUMN_NAME_BOARD_ROW INTEGER NOT NULL," +
            " $COLUMN_NAME_STONE TEXT," +
            " UNIQUE($COLUMN_NAME_BOARD_COLUMN, $COLUMN_NAME_BOARD_ROW) ON CONFLICT REPLACE)"

    const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS $TABLE_NAME"

    object OmokContract {
        const val TABLE_NAME = "omok"
        const val COLUMN_NAME_BOARD_COLUMN = "column"
        const val COLUMN_NAME_BOARD_ROW = "row"
        const val COLUMN_NAME_STONE = "stone"
    }
}
