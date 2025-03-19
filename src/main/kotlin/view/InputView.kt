package view

import Col
import Position
import Row
import Stone
import StoneColor

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

    private fun StoneColor.toDisplay(): String {
        return when (this) {
            StoneColor.BLACK -> "흑"
            StoneColor.WHITE -> "백"
        }
    }

    private fun Position.toDisplay(): String {
        return "${this.col.toDisplay()}${this.row.toDisplay()}"
    }

    private fun Row.toDisplay(): Int {
        return this.value
    }

    private fun Col.toDisplay(): Char {
        return (this.value + 64).toChar()
    }

    companion object {
        private const val TURN_MESSAGE_FORMAT = "%s의 차례입니다."
        private const val LAST_STONE_POSITION_MESSAGE = " (마지막 돌의 위치: %s)"
        private const val INPUT_MESSAGE_GUIDE = "\n위치를 입력하세요: "
    }
}
