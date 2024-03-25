package omok.view

import omok.model.Point

object InputView {
    private const val MIN_POINT = 0

    fun readPoint(boardSize: Int): Point {
        print("위치를 입력하세요 : ")
        val input = readln()
        val x: Int = (input[0]) - 'A'
        val y: Int = input.substring(1).toIntOrNull()?.let { it - 1 } ?: -1
        require(x in MIN_POINT until boardSize && y in MIN_POINT until boardSize)
        return Point(x, y)
    }
}
