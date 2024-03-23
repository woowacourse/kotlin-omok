package omok.view

import omok.view.model.PositionUiModel

class ConsoleOmokInputView : OmokInputView {
    override fun readPosition(): PositionUiModel {
        print("위치를 입력하세요 : ")
        val input = readln().trim()
        val inputX = input.firstOrNull()?.formatToPositionX() ?: return readPosition()
        val inputY = input.substring(1).toIntOrNull() ?: return readPosition()
        val (x, y) = inputX to inputY
        return PositionUiModel(x, y)
    }

    private fun Char.formatToPositionX(): Int = uppercase().first() - 'A' + 1
}
