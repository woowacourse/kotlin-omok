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
    ): String {
        print(TURN_MESSAGE_FORMAT.format(color.toDisplay()))
        lastStone?.let {
            println(LAST_STONE_POSITION_MESSAGE.format(lastStone.position.toDisplay()))
        }
        print(INPUT_MESSAGE_GUIDE)
        return readln()
    }

    private fun StoneColor.toDisplay(): String =
        when (this) {
            StoneColor.BLACK -> "흑"
            StoneColor.WHITE -> "백"
        }

    private fun Position.toDisplay(): String = "${this.col.toDisplay()}${this.row.toDisplay()}"

    private fun Row.toDisplay(): Int = this.value

    private fun Col.toDisplay(): Char = (this.value + 64).toChar()

    companion object {
        private const val TURN_MESSAGE_FORMAT = "\n%s의 차례입니다."
        private const val LAST_STONE_POSITION_MESSAGE = " (마지막 돌의 위치: %s)"
        private const val INPUT_MESSAGE_GUIDE = "\n위치를 입력하세요: "
    }
}
