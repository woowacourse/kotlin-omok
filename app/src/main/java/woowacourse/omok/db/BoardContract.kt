package woowacourse.omok.db

object BoardContract {
    const val TABLE_NAME = "board"
    const val COLUMN_NAME_NICKNAME = "nickname"
    const val COLUMN_NAME_POSITION = "position"
    const val COLUMN_NAME_STONE = "stone"

    const val CREATE_TABLE = "CREATE TABLE $TABLE_NAME " +
        "(" +
        "$COLUMN_NAME_NICKNAME VARCHAR(4)," +
        "$COLUMN_NAME_POSITION INT," +
        "$COLUMN_NAME_STONE INT NOT NULL," +
        "PRIMARY KEY ($COLUMN_NAME_NICKNAME, $COLUMN_NAME_POSITION)," +
        "FOREIGN KEY ($COLUMN_NAME_NICKNAME)" +
        "REFERENCES ${PlayerContract.TABLE_NAME} (${PlayerContract.COLUMN_NAME_NICKNAME})" +
        "ON DELETE CASCADE" +
        ");"
    const val DROP_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"
}
