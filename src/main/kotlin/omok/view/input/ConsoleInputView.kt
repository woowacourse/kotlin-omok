package omok.view.input

import omok.model.entity.Point

class ConsoleInputView : InputView {
    override fun getStonePoint(): Point {
        print("위치를 입력하세요: ")
        val input = readln()
        val x = alphabetToInt(input[0])
        val y = input.substring(1).toInt()
        return Point(x, y)
    }

    private fun alphabetToInt(alphabet: Char): Int = alphabet - 'A' + 1
}
