package omok.view

import omok.model.domain.omokboard.ColumnPosition

fun ColumnPosition.toEnglish(): Char {
    val alphabets = ('A'..'Z').toList()
    return alphabets[this.value - 1]
}
