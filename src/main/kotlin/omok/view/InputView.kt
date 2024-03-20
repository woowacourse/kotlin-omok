package omok.view

import omok.model.Stone

class InputView {
    fun getStone(color: Boolean): Stone {
        println("${getColor(color)}의 차례입니다.")
        print("위치를 입력하세요 : ")
        val input = readln()
        return Stone((input[0] - 'A') + 1, input[1].toString().toInt())
    }

    private fun getColor(color: Boolean): String = if (color) "백" else "흑"
}
