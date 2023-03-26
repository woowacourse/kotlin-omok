package woowacourse.omok.data.database.contract

internal const val DB_NAME: String = "omok.db"
internal const val DB_VERSION: Int = 6

const val POINT_TABLE_NAME: String = "points"
internal const val CREATE_POINTS_TABLE_QUERY = "CREATE TABLE $POINT_TABLE_NAME (" +
    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
    "x INTEGER NOT NULL," +
    "y INTEGER NOT NULL" +
    ");"
internal const val DROP_POINTS_TABLE_QUERY = "DROP TABLE IF EXISTS $POINT_TABLE_NAME"
