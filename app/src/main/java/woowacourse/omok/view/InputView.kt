package woowacourse.omok.view

import woowacourse.omok.domain.model.Point

class InputView {
    fun getPoint(boardSize: Int): Point =
        runCatching {
            print(MESSAGE_INPUT_POINT)
            val input = readln().trim()
            validateInput(input)
            parseInput(input, boardSize)
        }.getOrElse {
            println(it.message)
            getPoint(boardSize)
        }

    private fun validateInput(input: String) {
        require(input.isNotBlank()) { ERROR_INVALID_INPUT }
        require(input[0] in VALID_X_RANGE) { ERROR_INVALID_INPUT }
        require(input.substring(1).toIntOrNull() != null) { ERROR_INVALID_INPUT }
    }

    private fun parseInput(
        input: String,
        boardSize: Int,
    ): Point {
        val row = input[0] - FIRST_X
        val col = input.substring(1).toInt() - 1
        Point.validate(row, col, boardSize)
        return Point(row, col)
    }

    companion object {
        private const val MESSAGE_INPUT_POINT = "\n위치를 입력하세요: "
        private const val ERROR_INVALID_INPUT = "[ERROR] 잘못된 입력입니다. 다시 시도하세요."
        private val VALID_X_RANGE = 'A'..'Z'
        private const val FIRST_X = 'A'
    }
}
