package woowacourse.omok.data.database

internal const val DB_NAME: String = "omok.db"
internal const val DB_VERSION: Int = 1

const val PLAYERS_TABLE_NAME: String = "players"
internal const val CREATE_PLAYERS_TABLE_QUERY = "CREATE TABLE $PLAYERS_TABLE_NAME (" +
        "player_id INTEGER PRIMARY KEY AUTOINCREMENT," +
        "player_name VARCHAR(20) NOT NULL," +
        "stone_color int" +
        ");"
internal const val DROP_PLAYERS_TABLE_QUERY = "DROP TABLE IF EXISTS $PLAYERS_TABLE_NAME"

const val POINT_TABLE_NAME: String = "points"
internal const val CREATE_POINTS_TABLE_QUERY = "CREATE TABLE $POINT_TABLE_NAME (" +
        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
        "player_id INTEGER," +
        "x int NOT NULL," +
        "y int NOT NULL," +
        "put_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
        ");"
internal const val DROP_POINTS_TABLE_QUERY = "DROP TABLE IF EXISTS $POINT_TABLE_NAME"

const val GAMES_TABLE_NAME: String = "games"
internal const val CREATE_GAMES_TABLE_QUERY = "CREATE TABLE $GAMES_TABLE_NAME (" +
        "player_id INTEGER PRIMARY KEY AUTOINCREMENT," +
        "player_name VARCHAR(20) NOT NULL," +
        "stone_color INTEGER" +
        ");"
internal const val DROP_GAMES_TABLE_QUERY = "DROP TABLE IF EXISTS $GAMES_TABLE_NAME"
