package omok.view

import omok.model.Point

object InputView {
    fun readPoint(boardSize: Int): Point {
        print("위치를 입력하세요 : ")
        val input = readln()
        val x: Int = (input[0]) - 'A'
        val y: Int = input.substring(1).toIntOrNull()?.let { it - 1 } ?: -1
        return Point(x, y, boardSize)
    }
}
