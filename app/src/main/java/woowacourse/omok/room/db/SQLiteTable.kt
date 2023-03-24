package woowacourse.omok.room.db

interface SQLiteTable {
    val name: String
    val scheme: List<Column>
}
