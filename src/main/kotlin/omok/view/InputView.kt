package omok.view

import omok.model.Intersection
import omok.model.PlayerColor
import rule.wrapper.point.Point

class InputView {
    fun readInitialTurn(): Point {
        println(MESSAGE_INITIAL_TURN_INDICATOR)
        return readPoint()
    }

    fun readTurn(intersection: Intersection): Point {
        val lastStone: PlayerColor = intersection.state
        print(
            MESSAGE_TURN_INDICATOR.format(
                when (lastStone) {
                    PlayerColor.WHITE -> "흑"
                    PlayerColor.BLACK -> "백"
                },
            ),
        )
        println(MESSAGE_LAST_STONE_POINT.format(intersection.point.stringRepresentation()))
        return readPoint()
    }

    private fun readPoint(): Point {
        return runCatching {
            print(MESSAGE_ENTER_POINT)
            val input: String = readln()
            val col: Int = input[0].integerRepresentation()
            val row: Int = input.substring(1).toInt()
            Point(row, col)
        }.getOrElse {
            readPoint()
        }
    }

    private fun Char.integerRepresentation(): Int {
        return this.uppercase()[0].code - 64
    }

    private fun Point.stringRepresentation(): String {
        return "${(this.col + 64).toChar()}${this.row}"
    }

    companion object {
        const val MESSAGE_TURN_INDICATOR = "%s의 차례입니다. "
        const val MESSAGE_INITIAL_TURN_INDICATOR = "흑의 차례입니다."
        const val MESSAGE_LAST_STONE_POINT = "(마지막 돌의 위치: %s)"
        const val MESSAGE_ENTER_POINT = "위치를 입력하세요: "
    }
}
