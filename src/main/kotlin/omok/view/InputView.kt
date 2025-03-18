package omok.view

import omok.model.Intersection
import omok.model.IntersectionState
import omok.model.Position

class InputView {
    fun readInitialTurn() {
        println(MESSAGE_INITIAL_TURN_INDICATOR)
        readPosition()
    }

    fun readTurn(intersection: Intersection) {
        val lastStone: IntersectionState = intersection.state
        println(
            MESSAGE_TURN_INDICATOR.format(
                when (lastStone) {
                    IntersectionState.WHITE -> "흑"
                    IntersectionState.BLACK -> "백"
                    IntersectionState.EMPTY -> ""
                },
            ),
        )
        println(MESSAGE_LAST_STONE_POSITION.format(intersection.position.stringRepresentation()))
        readPosition()
    }

    private fun readPosition(): Position {
        return runCatching {
            println(MESSAGE_ENTER_POSITION)
            val input: String = readln()
            println()
            val column: Int = input[0].code - 64
            val row: Int = input.substring(1).toInt()
            Position.of(row, column)
        }.getOrElse {
            readPosition()
        }
    }

    private fun Char.integerRepresentation(): Int {
        return this.code - 64
    }

    private fun Position.stringRepresentation(): String {
        return "${(this.column.value + 64).toChar()}${this.row.value}"
    }

    companion object {
        const val MESSAGE_TURN_INDICATOR = "%s의 차례입니다."
        const val MESSAGE_INITIAL_TURN_INDICATOR = "흑의 차례입니다."
        const val MESSAGE_LAST_STONE_POSITION = "(마지막 돌의 위치: %s)"
        const val MESSAGE_ENTER_POSITION = "위치를 입력하세요: "
    }
}
