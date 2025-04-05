package view

import woowacourse.omok.domain.position.Col
import woowacourse.omok.domain.position.Position
import woowacourse.omok.domain.position.Row
import woowacourse.omok.domain.stone.Stone
import woowacourse.omok.domain.stone.StoneColor

class InputView {
    fun readPosition(
        color: StoneColor,
        lastStone: Stone?,
    ): Position {
        print(TURN_MESSAGE_FORMAT.format(color.toDisplay()))
        lastStone?.let { println(LAST_STONE_POSITION_MESSAGE.format(lastStone.position.toDisplay())) }
        print(INPUT_MESSAGE_GUIDE)
        return readln().toPosition() ?: readPosition(color, lastStone)
    }

    private fun StoneColor.toDisplay(): String =
        when (this) {
            StoneColor.BLACK -> "흑"
            StoneColor.WHITE -> "백"
        }

    private fun String.toPosition(): Position? {
        if (this.length < 2) return null
        val (col, row) =
            splitToPositionElements(this) ?: run {
                println(ERROR_INVALID_POSITION_MESSAGE)
                return null
            }
        return Position(Row.from(row), Col.from(col))
    }

    private fun splitToPositionElements(input: String): Pair<Char, Int>? {
        val col = input[0]
        val row = input.substring(1).toIntOrNull() ?: return null
        return col to row
    }

    private fun Position.toDisplay(): String = "${this.col.toDisplay()}${this.row.toDisplay()}"

    private fun Row.toDisplay(): Int = this.value

    private fun Col.toDisplay(): Char = (this.value + 64).toChar()

    companion object {
        private const val TURN_MESSAGE_FORMAT = "\n%s의 차례입니다."
        private const val LAST_STONE_POSITION_MESSAGE = " (마지막 돌의 위치: %s)"
        private const val INPUT_MESSAGE_GUIDE = "\n위치를 입력하세요: "
        private const val ERROR_INVALID_POSITION_MESSAGE = "올바른 좌표를 다시 입력해주세요."
    }
}
