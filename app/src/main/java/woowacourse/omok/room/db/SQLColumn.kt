package woowacourse.omok.room.db

data class SQLColumn(
    val name: String,
    val type: SQLType,
    val constraint: String = ""
)
