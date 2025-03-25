package omok.domain.omokboard

@JvmInline
value class ColumnPosition(
    val value: Int,
) {
    companion object {
        private val ALPHABETS: CharRange = ('A'..'Z')

        fun fromChar(alphabet: Char): ColumnPosition {
            val alphabets = ALPHABETS.toList()
            return ColumnPosition(alphabets.indexOf(alphabet) + 1)
        }

        fun toLabel(columnPosition: ColumnPosition): Char {
            val alphabets = ALPHABETS.toList()
            return alphabets[columnPosition.value - 1]
        }
    }
}
