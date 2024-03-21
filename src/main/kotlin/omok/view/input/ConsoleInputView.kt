package omok.view.input

import omok.model.entity.Point

class ConsoleInputView : InputView {
    override tailrec fun getStonePoint(): Point {
        print("위치를 입력하세요: ")
        val input = readln()
        val inputX = input[0]
        val isInputXValid = inputX in 'A'..'O'

        val inputY = input.substring(1).toIntOrNull() ?: return getStonePoint()
        val isInputYValid = inputY in 1..15

        if (!(isInputXValid && isInputYValid)) {
            return getStonePoint()
        }

        val x = alphabetToInt(inputX)
        val y = inputY

        return Point(x, y)
    }

    private fun alphabetToInt(alphabet: Char): Int = alphabet - 'A' + 1
}
