package woowacourse.omok.domain.board

@JvmInline
value class Column(val value: Int) {
    companion object {
        val WALL = Column(-1)
    }
}
