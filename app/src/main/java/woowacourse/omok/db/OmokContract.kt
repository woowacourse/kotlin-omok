package woowacourse.omok.db

import android.provider.BaseColumns

object OmokContract : BaseColumns {
    const val COLUMN_COLOR_BLACK = "black"
    const val COLUMN_COLOR_WHITE = "white"

    object DB {
        const val SQL_CREATE_ENTRIES = """
            CREATE TABLE ${Entry.TABLE_NAME} (
                ${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT,
                ${Entry.COLUMN_STONE_COLOR} TEXT NOT NULL
                    CHECK (${Entry.COLUMN_STONE_COLOR}
                        IN ('${COLUMN_COLOR_BLACK}', '${COLUMN_COLOR_WHITE}')),
                ${Entry.COLUMN_STONE_ROW} INTEGER NOT NULL,
                ${Entry.COLUMN_STONE_COL} INTEGER NOT NULL,
                ${Entry.COLUMN_STONE_ORDER} INTEGER NOT NULL
            );
        """

        const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS ${Entry.TABLE_NAME}"
    }

    object Entry {
        const val TABLE_NAME = "stones"
        const val COLUMN_STONE_COLOR = "color"
        const val COLUMN_STONE_ROW = "row"
        const val COLUMN_STONE_COL = "column"
        const val COLUMN_STONE_ORDER = "place_order"
    }
}
