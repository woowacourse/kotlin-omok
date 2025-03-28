package woowacourse.omok.model.database

object OmokDBContract {
    object StonesTable {
        const val TABLE_NAME = "stones"
        const val COLUMN_NAME_ORDER = "stone_order"
        const val COLUMN_NAME_ROW_INDEX = "row_index"
        const val COLUMN_NAME_COL_INDEX = "col_index"
        const val COLUMN_NAME_STONE_COLOR = "stone_color"

        const val SQL_CREATE_ENTRIES =
            "CREATE TABLE $TABLE_NAME (" +
                "$COLUMN_NAME_ORDER INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COLUMN_NAME_ROW_INDEX INTEGER NOT NULL, " +
                "$COLUMN_NAME_COL_INDEX INTEGER NOT NULL, " +
                "$COLUMN_NAME_STONE_COLOR TEXT NOT NULL)"

        const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS $TABLE_NAME"
    }
}
