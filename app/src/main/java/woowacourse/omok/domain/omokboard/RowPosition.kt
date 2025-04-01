package woowacourse.omok.domain.omokboard

@JvmInline
value class RowPosition(
    val value: Int,
) {
    override fun toString(): String = value.toString()
}
