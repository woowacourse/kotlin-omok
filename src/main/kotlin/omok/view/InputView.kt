package omok.view

import omok.model.board.Board
import omok.model.position.Col
import omok.model.position.Position
import omok.model.position.Row
import omok.model.stone.GoStone
import omok.model.stone.Stone

class InputView {
    fun readPosition(stone: GoStone): Position {
        val input = requirePositionInput(stone)
        val row = input[0]
        val col =
            input.substring(1).toIntOrNull() ?: run {
                println(INVALID_AXIS_POSITION_MESSAGE)
                lineBreak()
                return readPosition(stone)
            }
        return Position(Row(row), Col.from(col))
    }

    private fun requirePositionInput(stone: GoStone): String {
        print(TURN_MESSAGE.format(stone.stoneType.value()))
        Board.getLastStonePosition()?.let {
            print(LAST_STONE_MESSAGE.format(it.toString()))
        }
        lineBreak()
        print(ENTER_POSITION_MESSAGE)
        return readln()
    }

    private fun lineBreak() = println()

    private fun Stone.value() =
        when (this) {
            Stone.BLACK_STONE -> "흑"
            Stone.WHITE_STONE -> "백"
            Stone.NONE -> ""
        }

    companion object {
        private const val TURN_MESSAGE = "%s의 차례입니다."
        private const val LAST_STONE_MESSAGE = "(마지막 돌의 위치: %s)"
        private const val ENTER_POSITION_MESSAGE = "위치를 입력하세요: "
        private const val INVALID_AXIS_POSITION_MESSAGE = "x 축은 숫자만 입력 가능합니다."
    }
}
