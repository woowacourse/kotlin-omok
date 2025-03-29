package woowacourse.omok.view

import woowacourse.omok.model.board.Board
import woowacourse.omok.model.board.BoardSize
import woowacourse.omok.model.board.Point
import woowacourse.omok.model.board.PointState

fun String.toPosition(): Pair<Int, Int> = this[0].alphabetToInt() to this.substring(1).toInt()

fun Char.alphabetToInt(): Int = if (this.uppercaseChar() in 'A'..'O') this.uppercaseChar() - 'A' + 1 else -1

fun Point.toUiString(): String = this.x.toAlphabet() + this.y.toString()

fun Int.toAlphabet(): Char = if (this in 1..15) 'A' + (this - 1) else ' '

fun Point?.toColorString(): String =
    when (this?.state) {
        PointState.BLACK -> "흑"
        PointState.WHITE -> "백"
        else -> "흑"
    }

fun Point?.toNextTurnColor(): String =
    when (this?.state) {
        PointState.BLACK -> "백"
        PointState.WHITE -> "흑"
        else -> "흑"
    }

fun Board.toUiString(): String {
    val sb = StringBuilder()

    for (y in this.size downTo 1) {
        sb.append(y.toString().padStart(2, ' ') + " ")
        for (x in BoardSize.BOARD_MIN_SIZE..this.size) {
            val point = this.points.find { it.x == x && it.y == y }
            sb.append(getBoardCharacter(this, x, y, point))
            if (x != this.size) sb.append("──")
        }
        sb.append("\n")
    }
    sb.append("   A  B  C  D  E  F  G  H  I  J  K  L  M  N  O\n")

    return sb.toString()
}

private fun getBoardCharacter(
    board: Board,
    x: Int,
    y: Int,
    point: Point?,
): String =
    point?.stateToUiString() ?: when {
        // 네 모서리 처리
        x == BoardSize.BOARD_MIN_SIZE && y == board.size -> "┌"
        x == board.size && y == board.size -> "┐"
        x == BoardSize.BOARD_MIN_SIZE && y == BoardSize.BOARD_MIN_SIZE -> "└"
        x == board.size && y == BoardSize.BOARD_MIN_SIZE -> "┘"

        // 상단, 하단 테두리 처리
        y == board.size -> "┬"
        y == BoardSize.BOARD_MIN_SIZE -> "┴"

        // 좌측, 우측 테두리 처리
        x == BoardSize.BOARD_MIN_SIZE -> "├"
        x == board.size -> "┤"

        // 기본 교차점 처리
        else -> "┼"
    }

private fun Point.stateToUiString(): String? =
    when (this.state) {
        PointState.BLACK -> "●"
        PointState.WHITE -> "○"
        PointState.OPEN -> null
    }
