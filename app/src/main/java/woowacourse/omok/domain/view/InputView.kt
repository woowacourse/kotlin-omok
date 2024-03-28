package woowacourse.omok.domain.view

import woowacourse.omok.domain.model.Board
import woowacourse.omok.domain.model.Point

object InputView {
    private const val MIN_POINT = 0

    fun readPoint(board: Board): Point {
        print("위치를 입력하세요 : ")
        val input = readln()
        val x: Int = (input[0]) - 'A'
        val y: Int = input.substring(1).toIntOrNull()?.let { it - 1 } ?: -1
        val point = Point(x, y)
        require(x in MIN_POINT until board.size && y in MIN_POINT until board.size)
        return point
    }
}
