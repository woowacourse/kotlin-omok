package omok.domain.omokboard

import omok.view.InputView.Companion.ALPHABETS

@JvmInline
value class ColumnPosition(
    val value: Int,
) {
    companion object {
        fun fromChar(alphabet: Char): ColumnPosition {
            val alphabets = ALPHABETS.toList()
            return ColumnPosition(alphabets.indexOf(alphabet) + 1)
        }
    }
}
