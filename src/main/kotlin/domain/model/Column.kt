package domain.model

@JvmInline
value class Column private constructor(val value: Int) {
    companion object {
        private val COLUMNS = ('A'..'O').toList()
        private const val INDEXING = 1

        fun from(value: Char): Column {
            require(value in ('A'..'O')) { "잘못된 위치입니다." }
            return Column(COLUMNS.indexOf(value) + INDEXING)
        }
    }
}
