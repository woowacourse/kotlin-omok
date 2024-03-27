package omok.view

import omok.view.model.PositionUiModel

class ConsoleOmokInputView : OmokInputView {
    override fun readPosition(): PositionUiModel {
        print("위치를 입력하세요 : ")
        val input = readln().trim()
        val inputX = input.firstOrNull()?.formatToPositionX() ?: return showInvalidInputPosition(::readPosition)
        val inputY = input.substring(1).toIntOrNull() ?: return showInvalidInputPosition(::readPosition)
        val (x, y) = inputX to inputY
        return PositionUiModel(x, y)
    }

    private inline fun showInvalidInputPosition(onError: () -> PositionUiModel): PositionUiModel {
        println("잘못된 입력입니다. 다시 입력해주세요. 예시 입력) A1")
        return onError()
    }

    private fun Char.formatToPositionX(): Int = uppercase().first() - 'A' + 1
}
