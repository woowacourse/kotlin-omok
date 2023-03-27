package woowacourse.omok.data.db

interface SQLiteTable {
    val name: String
    val scheme: List<Column>
}
