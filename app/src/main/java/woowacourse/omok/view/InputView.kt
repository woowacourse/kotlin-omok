package woowacourse.omok.view

import woowacourse.omok.model.stone.Point
import woowacourse.omok.model.stone.Stone
import woowacourse.omok.model.stone.StoneColor

class InputView {
    fun readTurn(lastStone: Stone?): Point {
        if (lastStone == null) {
            println(MESSAGE_INITIAL_TURN_INDICATOR)
        } else {
            val currentColor: StoneColor = lastStone.color.reverse()
            print(
                MESSAGE_TURN_INDICATOR.format(
                    when (currentColor) {
                        StoneColor.WHITE -> "백"
                        StoneColor.BLACK -> "흑"
                    },
                ),
            )
            println(MESSAGE_LAST_STONE_POINT.format(lastStone.point.stringRepresentation()))
        }
        return readPoint()
    }

    private fun readPoint(): Point =
        runCatching {
            print(MESSAGE_ENTER_POINT)
            val input: String = readln()
            val col: Int = input[0].integerRepresentation()
            val row: Int = input.substring(1).toInt()
            Point(row, col)
        }.getOrElse {
            println(it.message)
            readPoint()
        }

    private fun Char.integerRepresentation(): Int = this.uppercase()[0].code - ASCII_OFFSET

    private fun Point.stringRepresentation(): String = "${(this.col + woowacourse.omok.view.InputView.ASCII_OFFSET).toChar()}${this.row}"

    companion object {
        const val MESSAGE_TURN_INDICATOR = "%s의 차례입니다. "
        const val MESSAGE_INITIAL_TURN_INDICATOR = "흑의 차례입니다."
        const val MESSAGE_LAST_STONE_POINT = "(마지막 돌의 위치: %s)"
        const val MESSAGE_ENTER_POINT = "위치를 입력하세요: "
        private const val ASCII_OFFSET = 'A'.code - 1
    }
}
