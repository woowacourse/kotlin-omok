package woowacourse.omok.view

import woowacourse.omok.model.Column
import woowacourse.omok.model.Coordinate
import woowacourse.omok.model.Row

object InputView {
    private const val MESSAGE_INPUT_COORDINATE = "위치를 입력하세요: "
    private const val ERROR_INVALID_COLUMN_INPUT = "열의 입력은 영문자 한 글자여야 합니다."
    private const val ERROR_INVALID_ROW_INPUT = "행의 입력은 문자가 아닌 정수이어야 합니다."

    fun inputStoneCoordinate(): Coordinate {
        print(MESSAGE_INPUT_COORDINATE)
        val input = readlnOrNull().orEmpty()
        val result = input.validateCoordinate()

        return result.getOrElse { throwable ->
            println("${throwable.message}")
            inputStoneCoordinate()
        }
    }

    fun String.validateCoordinate(): Result<Coordinate> {
        return runCatching {
            Coordinate(
                Row(validateStringToInt(this.substring(1))),
                Column(changeAlphabetToInt(this.substring(0, 1))),
            )
        }
    }

    fun validateStringToInt(input: String): Int {
        requireNotNull(input.toIntOrNull()) { ERROR_INVALID_ROW_INPUT }
        return input.toInt()
    }

    fun changeAlphabetToInt(input: String): Int {
        require(input.length == 1) { ERROR_INVALID_COLUMN_INPUT }
        require(input.matches("^[a-zA-Z]$".toRegex())) { ERROR_INVALID_COLUMN_INPUT }
        return if (input.matches("^[a-z]$".toRegex())) {
            input.first() - 'a' + 1
        } else {
            input.first() - 'A' + 1
        }
    }
}
