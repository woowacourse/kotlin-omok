package view

import domain.Stone
import domain.XCoordinate
import domain.YCoordinate

object InputView {

    private const val INPUT_POINT = "위치를 입력하세요: "
    private const val NOT_CORRECT_TYPE_ERROR = "정확한 좌표를 입력바랍니다."

    fun readStone(): Stone {
        kotlin.runCatching {
            val point = tryToGetPointUntilCorrectType()
            return Stone(XCoordinate.of(point[0]), YCoordinate.of(point.substring(1).toInt()))
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
