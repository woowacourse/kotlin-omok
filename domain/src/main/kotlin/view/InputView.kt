package view

import domain.*
import domain.stone.Point

object InputView {

    private const val INPUT_POINT = "위치를 입력하세요: "
    private const val NOT_CORRECT_TYPE_ERROR = "정확한 좌표를 입력바랍니다."

    fun readPoint(): Point {
        kotlin.runCatching {
            val point = tryToGetPointUntilCorrectType()
            val x = point[0].uppercase()[0]
            val y = point.substring(1).toInt()
            require(x in MIN_VIEW_X..MAX_VIEW_X) { "x의 범위가 좌표를 넘어갑니다." }
            require(y in MIN_VIEW_Y..MAX_VIEW_Y) { "y의 범위가 좌표를 넘어갑니다." }
            return Point.create(x, y)
        }.onFailure {
            println(it.message)
        }
        return readPoint()
    }

    private fun tryToGetPointUntilCorrectType(): String {
        print(INPUT_POINT)
        var inputValue = readln()
        while (inputValue.substring(1).toIntOrNull() == null) {
            println(NOT_CORRECT_TYPE_ERROR)
            print(INPUT_POINT)
            inputValue = readln()
        }
        return inputValue
    }
}
