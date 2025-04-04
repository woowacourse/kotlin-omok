package woowacourse.omok.console.view

class InputView {
    fun readPosition(): String {
        print(MESSAGE_INPUT_POSITION)
        val input = readlnOrNull()
        if (input == null) {
            println(ERROR_INVALID_INPUT)
            return readPosition()
        }
        return input
    }

    companion object {
        private const val MESSAGE_INPUT_POSITION = "위치를 입력하세요:"
        private const val ERROR_INVALID_INPUT = "유효하지 않은 입력입니다. 다시 입력해주세요."
    }
}
