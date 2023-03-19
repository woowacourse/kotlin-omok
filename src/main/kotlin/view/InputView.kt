package view

import domain.Stone

object InputView {

    private const val INPUT_POINT = "위치를 입력하세요: "
    private const val NOT_CORRECT_TYPE_ERROR = "정확한 좌표를 입력바랍니다."

    fun readStone(): Stone {
        kotlin.runCatching {
            val point = tryToGetPointUntilCorrectType()
            val x = point[0].uppercase()[0]
            val y = point.substring(1).toInt()
            require(x in 'A'..'O') { "x의 범위가 좌표를 넘어갑니다." }
            require(y in 1..15) { "y의 범위가 좌표를 넘어갑니다." }
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
