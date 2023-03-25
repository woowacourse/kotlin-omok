package woowacourse.omok.db

object PlayerContract {
    const val TABLE_NAME = "player"
    const val COLUMN_NAME_NICKNAME = "nickname"

    const val CREATE_TABLE = "CREATE TABLE $TABLE_NAME ($COLUMN_NAME_NICKNAME VARCHAR(4) PRIMARY KEY)"
    const val DROP_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"
}
