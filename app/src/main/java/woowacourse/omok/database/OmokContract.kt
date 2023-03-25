package woowacourse.omok.database

const val DB_NAME: String = "omok.db"
const val DB_VERSION: Int = 1
const val POINT_TABLE_NAME: String = "points"
const val CREATE_POINTS_TABLE_QUERY = "CREATE TABLE $POINT_TABLE_NAME (" +
    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
    "board_index," +
    "go_stone_color INTEGER NOT NULL," +
    "x INTEGER NOT NULL," +
    "y INTEGER NOT NULL" +
    ");"
const val DROP_POINTS_TABLE_QUERY = "DROP TABLE IF EXISTS $POINT_TABLE_NAME"
