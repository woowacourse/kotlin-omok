package omok.view

import omok.model.board.Board
import omok.model.position.Column
import omok.model.position.Position
import omok.model.position.Row
import omok.model.stone.GoStone
import omok.model.stone.StoneType

class InputView {
    fun readPosition(stone: GoStone): Position {
        val input = requirePositionInput(stone)
        val row = input[0]
        val column =
            input.substring(1).toIntOrNull() ?: run {
                println(INVALID_AXIS_POSITION_MESSAGE)
                lineBreak()
                return readPosition(stone)
            }
        return Position(Row(row), Column.from(column))
    }

    private fun requirePositionInput(stone: GoStone): String {
        print(TURN_MESSAGE.format(stone.stoneType.value()))
        Board.lastPosition?.let {
            print(LAST_STONE_MESSAGE.format(it.convert()))
        }
        lineBreak()
        print(ENTER_POSITION_MESSAGE)
        return readln()
    }

    private fun lineBreak() = println()

    private fun StoneType.value() =
        when (this) {
            StoneType.BLACK_STONE -> StoneType.BLACK_STONE.type
            StoneType.WHITE_STONE -> StoneType.WHITE_STONE.type
            StoneType.NONE -> StoneType.NONE.type
        }

    private fun Position.convert(): String = "${Row.X_AXIS_START + row.value}${column.value + 1}"

    companion object {
        private const val TURN_MESSAGE = "%s의 차례입니다."
        private const val LAST_STONE_MESSAGE = "(마지막 돌의 위치: %s)"
        private const val ENTER_POSITION_MESSAGE = "위치를 입력하세요: "
        private const val INVALID_AXIS_POSITION_MESSAGE = "x 축은 숫자만 입력 가능합니다."
    }
}
