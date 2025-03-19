package omok.model.domain.omokboard

@JvmInline
value class RowPosition(
    val value: Int,
) {
    override fun toString(): String = value.toString()
}
