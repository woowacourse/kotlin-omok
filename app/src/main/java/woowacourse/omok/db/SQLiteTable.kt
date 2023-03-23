package woowacourse.omok.db

interface SQLiteTable {
    val name: String
    val scheme: List<Column>
}
