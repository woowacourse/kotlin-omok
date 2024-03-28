package omok.view

import omok.model.board.Board
import omok.model.position.Position
import omok.model.stone.GoStone
import omok.model.stone.StoneType

class InputView {
    fun readPosition(stone: GoStone): Position {
        val input = requirePositionInput(stone)
        val row = input[ROW_INDEX]
        val column =
            input.substring(COLUMN_INDEX).toIntOrNull() ?: run {
                println(INVALID_AXIS_POSITION_MESSAGE)
                lineBreak()
                return readPosition(stone)
            }
        return Position.of(row, column)
    }

    private fun requirePositionInput(stone: GoStone): String {
        print(TURN_MESSAGE.format(stone.stoneType.value()))
        if (!Board.isLastPositionExist()) {
            print(LAST_STONE_MESSAGE.format(Board.getLastStonePosition().convert()))
        }
        lineBreak()
        print(ENTER_POSITION_MESSAGE)
        return readln()
    }

    private fun lineBreak() = println()

    private fun StoneType.value() =
        when (this) {
            StoneType.BLACK_STONE -> "흑"
            StoneType.WHITE_STONE -> "백"
            StoneType.NONE -> ""
        }

    companion object {
        private const val ROW_INDEX = 0
        private const val COLUMN_INDEX = 1
        private const val TURN_MESSAGE = "%s의 차례입니다."
        private const val LAST_STONE_MESSAGE = "(마지막 돌의 위치: %s)"
        private const val ENTER_POSITION_MESSAGE = "위치를 입력하세요: "
        private const val INVALID_AXIS_POSITION_MESSAGE = "x 축은 숫자만 입력 가능합니다."
    }
}
