package woowacourse.omok.view

import woowacourse.omok.domain.board.Board
import woowacourse.omok.domain.board.BoardSize
import woowacourse.omok.domain.board.CellState
import woowacourse.omok.domain.board.Point

fun String.toPosition(): Pair<Int, Int> = this[0].alphabetToInt() to this.substring(1).toInt()

fun Char.alphabetToInt(): Int = this.uppercaseChar().let { if (it in 'A'..'Z') it - 'A' + 1 else -1 }

fun Point.toAlphabet(): String = "${x.toAlphabet()}$y"

fun Int.toAlphabet(): Char = if (this in 1..15) 'A' + (this - 1) else ' '

fun CellState?.toColorString(): String =
    when (this) {
        CellState.BLACK -> "흑"
        CellState.WHITE -> "백"
        else -> "흑"
    }

fun Board.toUiString(): String {
    val sb = StringBuilder()

    for (y in this.size downTo 1) {
        sb.append(y.toString().padStart(2, ' ')).append(" ")
        for (x in BoardSize.MIN_SIZE..this.size) {
            sb.append(getBoardCharacter(this, x, y))
            if (x != this.size) sb.append("──")
        }
        sb.append("\n")
    }
    sb.append("   ")
    ('A'..'Z').take(this.size).forEach { sb.append("$it  ") }
    sb.append("\n")

    return sb.toString()
}

private fun getBoardCharacter(
    board: Board,
    x: Int,
    y: Int,
): String {
    val stoneColor = board.findStoneColor(Point(x, y))
    return stoneColor?.toUiString() ?: getBorderCharacter(x, y, board.size)
}

private fun getBorderCharacter(
    x: Int,
    y: Int,
    boardSize: Int,
): String =
    when {
        x == BoardSize.MIN_SIZE && y == boardSize -> "┌"
        x == boardSize && y == boardSize -> "┐"
        x == BoardSize.MIN_SIZE && y == BoardSize.MIN_SIZE -> "└"
        x == boardSize && y == BoardSize.MIN_SIZE -> "┘"
        y == boardSize -> "┬"
        y == BoardSize.MIN_SIZE -> "┴"
        x == BoardSize.MIN_SIZE -> "├"
        x == boardSize -> "┤"
        else -> "┼"
    }

private fun CellState.toUiString(): String? =
    when (this) {
        CellState.BLACK -> "●"
        CellState.WHITE -> "○"
        CellState.EMPTY -> null
    }
