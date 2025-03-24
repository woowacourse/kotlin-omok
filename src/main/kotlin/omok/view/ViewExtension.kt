package omok.view

import omok.model.board.Board
import omok.model.board.BoardSize
import omok.model.board.Point
import omok.model.board.StoneColor

fun String.toPosition(): Pair<Int, Int> {
    return this[0].alphabetToInt() to this.substring(1).toInt()
}

fun Char.alphabetToInt(): Int {
    return if (this.uppercaseChar() in 'A'..'Z') this.uppercaseChar() - 'A' + 1 else -1
}

fun Point.toAlphabet(): String {
    return this.x.toAlphabet() + this.y.toString()
}

fun Int.toAlphabet(): Char {
    return if (this in 1..15) 'A' + (this - 1) else ' '
}

fun StoneColor?.toColorString(): String {
    return when (this) {
        StoneColor.BLACK -> "흑"
        StoneColor.WHITE -> "백"
        else -> "흑"
    }
}

fun Board.toUiString(): String {
    val sb = StringBuilder()

    for (y in this.points.size.value downTo 1) {
        sb.append(y.toString().padStart(2, ' ') + " ")
        for (x in BoardSize.MIN_SIZE..this.points.size.value) {
            sb.append(getBoardCharacter(this, x, y))
            if (x != this.points.size.value) sb.append("──")
        }
        sb.append("\n")
    }
    sb.append("   ")
    ('A'..'Z').take(this.points.size.value).forEach { sb.append("$it  ") }
    sb.append("\n")

    return sb.toString()
}

private fun getBoardCharacter(
    board: Board,
    x: Int,
    y: Int,
): String {
    val stoneColor = board.findStoneColor(Point(x, y))

    return stoneColor?.toUiString() ?: when {
        // 네 모서리 처리
        x == BoardSize.MIN_SIZE && y == board.points.size.value -> "┌"
        x == board.points.size.value && y == board.points.size.value -> "┐"
        x == BoardSize.MIN_SIZE && y == BoardSize.MIN_SIZE -> "└"
        x == board.points.size.value && y == BoardSize.MIN_SIZE -> "┘"

        // 상단, 하단 테두리 처리
        y == board.points.size.value -> "┬"
        y == BoardSize.MIN_SIZE -> "┴"

        // 좌측, 우측 테두리 처리
        x == BoardSize.MIN_SIZE -> "├"
        x == board.points.size.value -> "┤"

        // 기본 교차점 처리
        else -> "┼"
    }
}

private fun StoneColor.toUiString(): String? {
    return when (this) {
        StoneColor.BLACK -> "●"
        StoneColor.WHITE -> "○"
    }
}
