package omok.view

import omok.model.GoStone
import omok.model.Position
import omok.model.Stone

class InputView {
    fun readPosition(stone: GoStone): Position {
        print("${stone.stoneType.value()}의 차례입니다.")
        val lastPosition = stone.getLastPosition()
        if (lastPosition != null) {
            print("(마지막 돌의 위치: ${lastPosition.convert()})")
        }
        lineBreak()
        print("위치를 입력하세요: ")
        val input = readln()
        val row = input[0]
        val col = input.substring(1).toInt()
        return Position.of(row, col)
    }

    private fun lineBreak() = println()

    private fun Position.convert() = "${'A' + row}${col + 1}"

    private fun Stone.value() =
        when (this) {
            Stone.BLACK_STONE -> "흑"
            Stone.WHITE_STONE -> "백"
            Stone.NONE -> ""
        }
}
