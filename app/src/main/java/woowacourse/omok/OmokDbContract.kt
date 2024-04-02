package woowacourse.omok

import android.provider.BaseColumns

object OmokDbContract {
    const val SQL_CREATE_ENTRIES: String = """
        create table ${Places.TABLE_NAME} (
        ${BaseColumns._ID} integer primary key,
        ${Places.COLUMN_NAME_COLOR} text,
        ${Places.COLUMN_NAME_COL_COORDINATE} text,
        ${Places.COLUMN_NAME_ROW_COORDINATE} text)
    """

    const val SQL_DELETE_ALL_ENTRIES = "delete from ${Places.TABLE_NAME}"

    const val SQL_DROP_TABLE = "drop table if exists ${Places.TABLE_NAME}"

    object Places : BaseColumns {
        const val TABLE_NAME: String = "places"
        const val COLUMN_NAME_COLOR: String = "color"
        const val COLUMN_NAME_COL_COORDINATE: String = "rowCoordinate"
        const val COLUMN_NAME_ROW_COORDINATE: String = "colCoordinate"
    }
}
