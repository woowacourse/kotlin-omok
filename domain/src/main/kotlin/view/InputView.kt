package view

import domain.stone.Stone

object InputView {

    private const val INPUT_POINT_MESSAGE = "위치를 입력하세요: "
    private const val NOT_CORRECT_TYPE_ERROR = "정확한 좌표를 입력바랍니다."

    fun readStone(): Stone {
        print(INPUT_POINT_MESSAGE)
        var inputValue = readln()
        while (inputValue.length < 2 || inputValue.substring(1).toIntOrNull() == null) {
            println(NOT_CORRECT_TYPE_ERROR)
            print(INPUT_POINT_MESSAGE)
            inputValue = readln()
        }
        return Stone(inputValue[0], inputValue.substring(1).toInt())
    }
}
