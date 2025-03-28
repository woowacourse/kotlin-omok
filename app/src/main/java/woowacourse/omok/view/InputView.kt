package woowacourse.omok.view

import woowacourse.omok.domain.Position

class InputView {
    fun readPosition(): Position {
        print(MESSAGE_INPUT_POSITION)
        val input = readlnOrNull()
        if (input == null) {
            println(ERROR_INVALID_INPUT)
            return readPosition()
        }
        return PositionParser.encode(input)
    }

    companion object {
        private const val MESSAGE_INPUT_POSITION: String = "위치를 입력하세요:"
        private const val ERROR_INVALID_INPUT: String = "유효하지 않은 입력입니다. 다시 입력해 주세요."
    }
}
