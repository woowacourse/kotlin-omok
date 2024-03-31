package woowacourse.omok

import android.provider.BaseColumns

object FeedReaderContract {
    const val SQL_CREATE_ENTRIES: String = """
        create table ${FeedNotation.TABLE_NAME} (
        ${BaseColumns._ID} integer primary key,
        ${FeedNotation.COLUMN_NAME_COLOR} text,
        ${FeedNotation.COLUMN_NAME_COL_COORDINATE} text,
        ${FeedNotation.COLUMN_NAME_ROW_COORDINATE} text)
    """

    const val SQL_DELETE_ALL_ENTRIES = "delete from ${FeedNotation.TABLE_NAME}"

    const val SQL_DROP_TABLE = "drop table if exists ${FeedNotation.TABLE_NAME}"

    object FeedNotation : BaseColumns {
        const val TABLE_NAME: String = "notation"
        const val COLUMN_NAME_COLOR: String = "color"
        const val COLUMN_NAME_COL_COORDINATE: String = "rowCoordinate"
        const val COLUMN_NAME_ROW_COORDINATE: String = "colCoordinate"
    }
}
