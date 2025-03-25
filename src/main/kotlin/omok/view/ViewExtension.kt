package omok.view

import omok.model.board.Board
import omok.model.board.BoardSize
import omok.model.board.Point
import omok.model.board.StoneColor

fun String.toPosition(): Pair<Int, Int> = this[0].alphabetToInt() to this.substring(1).toInt()

fun Char.alphabetToInt(): Int = this.uppercaseChar().let { if (it in 'A'..'Z') it - 'A' + 1 else -1 }

fun Point.toAlphabet(): String = "${x.toAlphabet()}$y"

fun Int.toAlphabet(): Char = if (this in 1..15) 'A' + (this - 1) else ' '

fun StoneColor?.toColorString(): String =
    when (this) {
        StoneColor.BLACK -> "흑"
        StoneColor.WHITE -> "백"
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
    return when (val stoneColor = board.findStoneColor(Point(x, y))) {
        null -> getBorderCharacter(x, y, board.size)
        else -> stoneColor.toUiString() ?: "┼"
    }
}

private fun getBorderCharacter(
    x: Int,
    y: Int,
    boardSize: Int,
): String {
    return when {
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
}

private fun StoneColor.toUiString(): String =
    when (this) {
        StoneColor.BLACK -> "●"
        StoneColor.WHITE -> "○"
    }
