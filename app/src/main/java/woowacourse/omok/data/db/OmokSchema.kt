package woowacourse.omok.data.db

import android.provider.BaseColumns
import woowacourse.omok.data.db.OmokSchema.OmokContract.COLUMN_NAME_BOARD_COLUMN
import woowacourse.omok.data.db.OmokSchema.OmokContract.COLUMN_NAME_BOARD_ROW
import woowacourse.omok.data.db.OmokSchema.OmokContract.COLUMN_NAME_STONE
import woowacourse.omok.data.db.OmokSchema.OmokContract.TABLE_NAME

object OmokSchema {
    const val SQL_CREATE_ENTRIES =
        "CREATE TABLE $TABLE_NAME (" +
            " ${OmokContract.ID} INTEGER PRIMARY KEY AUTOINCREMENT not null," +
            "$COLUMN_NAME_BOARD_COLUMN INTEGER," +
            "$COLUMN_NAME_BOARD_ROW INTEGER," +
            "$COLUMN_NAME_STONE TEXT)"

    const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS $TABLE_NAME"

    object OmokContract : BaseColumns {
        const val TABLE_NAME = "omok"
        const val ID = BaseColumns._ID
        const val COLUMN_NAME_BOARD_COLUMN = "column"
        const val COLUMN_NAME_BOARD_ROW = "row"
        const val COLUMN_NAME_STONE = "stone"
    }
}
