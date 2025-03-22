package omok.view

import omok.domain.Point

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
        val point = Point(row, col)
        require(point.x in 0..<boardSize && point.y in 0..<boardSize) {
            ERROR_INVALID_POSITION.format(boardSize, boardSize)
        }
        return point
    }

    companion object {
        private const val MESSAGE_INPUT_POINT = "\n위치를 입력하세요: "
        private const val ERROR_INVALID_INPUT = "[ERROR] 잘못된 입력입니다. 다시 시도하세요."
        private const val ERROR_INVALID_POSITION = "[ERROR] 바둑판의 크기는 %dx%d입니다."
        private val VALID_X_RANGE = 'A'..'Z'
        private const val FIRST_X = 'A'
    }
}
