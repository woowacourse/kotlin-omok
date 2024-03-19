package omok.view

import omok.model.Point

object InputView {
    fun readPoint(): Point {
        print("위치를 입력하세요 : ")
        val input = readln()
        val row = input[0].code - 65
        val column = input.substring(1).toInt()
        return Point(row, column)
    }
}
