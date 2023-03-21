package view

import domain.Stone
import domain.X_MAX_RANGE
import domain.X_MIN_RANGE
import domain.Y_MAX_RANGE
import domain.Y_MIN_RANGE

object InputView {

    private const val INPUT_POINT = "위치를 입력하세요: "
    private const val NOT_CORRECT_TYPE_ERROR = "정확한 좌표를 입력바랍니다."

    fun readStone(): Stone {
        kotlin.runCatching {
            val point = tryToGetPointUntilCorrectType()
            val x = point[0].uppercase()[0]
            val y = point.substring(1).toInt()
            require(x in X_MIN_RANGE..X_MAX_RANGE) { "x의 범위가 좌표를 넘어갑니다." }
            require(y in Y_MIN_RANGE..Y_MAX_RANGE) { "y의 범위가 좌표를 넘어갑니다." }
            return Stone(x, y)
        }.onFailure {
            println(it.message)
        }
        return readStone()
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
