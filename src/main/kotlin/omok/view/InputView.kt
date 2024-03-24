package omok.view

import omok.model.Coordinate

object InputView {
    private const val INPUT_POSITION_MESSAGE = "위치를 입력하세요: "
    private const val BASE_CHAR = 'A'
    private const val BASE_NUMBER = 1
    private const val DIVIDE_INDEX = 1
    private const val ERROR_EMPTY_INPUT = "입력값이 필요합니다."

    fun readPosition(): Coordinate {
        print(INPUT_POSITION_MESSAGE)
        val input = readln().validateEmpty()
        val inputX = input.first().uppercaseChar() - BASE_CHAR
        val inputY =
            input.substring(DIVIDE_INDEX).toIntOrNull()?.minus(BASE_NUMBER) ?: throw IllegalArgumentException(ERROR_EMPTY_INPUT)
        return Coordinate(inputX, inputY)
    }

    private fun String.validateEmpty(): String {
        require(this.isNotEmpty()) { ERROR_EMPTY_INPUT }
        return this
    }
}
