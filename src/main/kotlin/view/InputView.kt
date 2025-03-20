package view

import Col
import Row
import Stone
import StoneColor
import rule.wrapper.position.Position

class InputView {
    fun readInputPosition(
        color: StoneColor,
        lastStone: Stone?,
    ): Pair<Char, Int> {
        print(TURN_MESSAGE_FORMAT.format(color.toDisplay()))
        lastStone?.let {
            println(LAST_STONE_POSITION_MESSAGE.format(lastStone.position.toDisplay()))
        }
        print(INPUT_MESSAGE_GUIDE)
        return splitInput(readln())
    }

    private fun StoneColor.toDisplay(): String =
        when (this) {
            StoneColor.BLACK -> "흑"
            StoneColor.WHITE -> "백"
        }

    private fun splitInput(input: String): Pair<Char, Int> {
        val col = input[0]
        val row =
            input.substring(1).toIntOrNull() ?: run {
                print(ERROR_MESSAGE_FORMAT.format(ERROR_COL_INPUT))
                return splitInput(readln())
            }
        return col to row
    }

    private fun Position.toDisplay(): String = "${this.col.toDisplay()}${this.row.toDisplay()}"

    private fun Row.toDisplay(): Int = this.value

    private fun Col.toDisplay(): Char = (this.value + 64).toChar()

    companion object {
        private const val TURN_MESSAGE_FORMAT = "\n%s의 차례입니다."
        private const val LAST_STONE_POSITION_MESSAGE = " (마지막 돌의 위치: %s)"
        private const val INPUT_MESSAGE_GUIDE = "\n위치를 입력하세요: "
        private const val ERROR_MESSAGE_FORMAT = "[ERROR] %s"
        private const val ERROR_COL_INPUT = "숫자가 아닌 행이 입력되었습니다."
    }
}
