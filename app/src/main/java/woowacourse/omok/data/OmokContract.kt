package woowacourse.omok.data

import android.provider.BaseColumns

object OmokContract {
    const val TABLE_NAME = "boards"

    const val COLUMN_NAME_ID = BaseColumns._ID
    const val COLUMN_NAME_STONE_COLOR = "stone_color"
    const val COLUMN_NAME_ROW = "row"
    const val COLUMN_NAME_COL = "column"

    const val SQL_CREATE_ENTRIES =
        "CREATE TABLE $TABLE_NAME (" +
            "$COLUMN_NAME_ID INTEGER PRIMARY KEY," +
            "$COLUMN_NAME_STONE_COLOR TEXT," +
            "$COLUMN_NAME_ROW INTEGER," +
            "$COLUMN_NAME_COL INTEGER);"

    const val SQL_DROP_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"

    const val SQL_INSERT_STONE =
        """ INSERT INTO $TABLE_NAME
            ($COLUMN_NAME_STONE_COLOR, $COLUMN_NAME_ROW, $COLUMN_NAME_COL)
            VALUES(?, ?, ?) 
        """

    const val SQL_FETCH_STONE_BY_POSITION =
        """ SELECT *
            FROM ${TABLE_NAME}
            WHERE $COLUMN_NAME_ROW = ? AND $COLUMN_NAME_COL = ?
        """

    const val SQL_DELETE_ENTRIES = "DELETE FROM ${TABLE_NAME}"
}
