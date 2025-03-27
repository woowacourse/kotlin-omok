package woowacourse.omok.domain.board

@JvmInline
value class Row(val value: Int) {
    companion object {
        val WALL = Row(-1)
    }
}
