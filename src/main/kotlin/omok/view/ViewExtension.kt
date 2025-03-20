package omok.view

import omok.model.board.Board
import omok.model.board.Point
import omok.model.board.PointState
import omok.model.stone.Position

fun String.toPosition(): Pair<Int, Int> {
    return this[0].alphabetToInt() to this.substring(1).toInt()
}

fun Char.alphabetToInt(): Int {
    return if (this.uppercaseChar() in 'A'..'O') this.uppercaseChar() - 'A' + 1 else -1
}

fun Position.toUiString(): String {
    return this.x.toAlphabet() + this.y.toString()
}

fun Int.toAlphabet(): Char {
    return if (this in 1..15) 'A' + (this - 1) else ' '
}

fun Point?.toColorString(): String {
    return when (this?.state) {
        PointState.BLACK -> "흑"
        PointState.WHITE -> "백"
        else -> "흑"
    }
}

fun Point?.toNextTurnColor(): String {
    return when (this?.state) {
        PointState.BLACK -> "백"
        PointState.WHITE -> "흑"
        else -> "흑"
    }
}

fun Board.toUiString(): String {
    val sb = StringBuilder()

    for (y in Board.BOARD_MAX_SIZE downTo 1) {
        sb.append(y.toString().padStart(2, ' ') + " ")
        for (x in Board.BOARD_MIN_SIZE..Board.BOARD_MAX_SIZE) {
            val point = this.points.find { it.position.x == x && it.position.y == y }
            sb.append(getBoardCharacter(x, y, point))
            if (x != Board.BOARD_MAX_SIZE) sb.append("──")
        }
        sb.append("\n")
    }
    sb.append("   A  B  C  D  E  F  G  H  I  J  K  L  M  N  O\n")

    return sb.toString()
}

private fun getBoardCharacter(
    x: Int,
    y: Int,
    point: Point?,
): String {
    return point?.toUiString() ?: when {
        // 네 모서리 처리
        x == Board.BOARD_MIN_SIZE && y == Board.BOARD_MAX_SIZE -> "┌"
        x == Board.BOARD_MAX_SIZE && y == Board.BOARD_MAX_SIZE -> "┐"
        x == Board.BOARD_MIN_SIZE && y == Board.BOARD_MIN_SIZE -> "└"
        x == Board.BOARD_MAX_SIZE && y == Board.BOARD_MIN_SIZE -> "┘"

        // 상단, 하단 테두리 처리
        y == Board.BOARD_MAX_SIZE -> "┬"
        y == Board.BOARD_MIN_SIZE -> "┴"

        // 좌측, 우측 테두리 처리
        x == Board.BOARD_MIN_SIZE -> "├"
        x == Board.BOARD_MAX_SIZE -> "┤"

        // 기본 교차점 처리
        else -> "┼"
    }
}

private fun Point.toUiString(): String? {
    return when (this.state) {
        PointState.BLACK -> "●"
        PointState.WHITE -> "○"
        PointState.OPEN -> null
    }
}
