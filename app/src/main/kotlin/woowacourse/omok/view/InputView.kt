package woowacourse.omok.view

class InputView : OmokInputView {
    override fun readPosition(): Pair<Int, Int> {
        print(REQUIRE_POSITION_MESSAGE)
        val input = readlnOrNull()?.replace(" ", "") ?: ""
        input.validateNullOrBlank()
        input.validatePosition()

        return input.toPosition()
    }

    private fun String.validateNullOrBlank() {
        require(this.isNotBlank()) { BLANK_INPUT_MESSAGE }
    }

    private fun String.validatePosition() {
        require(this[0].isLetter()) { INVALID_INPUT_MESSAGE }
        require(substring(1).all { it.isDigit() }) { INVALID_INPUT_MESSAGE }
    }

    companion object {
        private const val REQUIRE_POSITION_MESSAGE = "위치를 입력하세요: "
        private const val BLANK_INPUT_MESSAGE = "빈 문자열 입니다."
        private const val INVALID_INPUT_MESSAGE = "위치가 잘못 입력되었습니다."
    }
}
