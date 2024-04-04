package woowacourse.omok.view

import woowacourse.omok.model.omok.Position

object ConsoleOmokInputView : OmokInputView {
    override fun readPosition(): Position {
        print("위치를 입력하세요 : ")
        val input = readln().trim()
        val inputX = input.firstOrNull()?.formatToPositionX() ?: return readPosition()
        val inputY = input.substring(1).toIntOrNull() ?: return readPosition()
        val (x, y) = inputX to inputY
        return Position(x, y)
    }

    private fun Char.formatToPositionX(): Int = uppercase().first() - 'A' + 1
}
