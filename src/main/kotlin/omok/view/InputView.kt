package omok.view

class InputView {
    tailrec fun readCoordinateText(): String {
        print(INPUT_STONE_POSITION)
        val rawInput = readln().trim()
        if (rawInput.length >= 2) {
            return rawInput
        }
        inputExceptionAlert(ERROR_INVALID_LENGTH)
        return readCoordinateText()
    }

    fun inputExceptionAlert(exceptionText: String) {
        println(exceptionText)
    }

    companion object {
        private const val INPUT_STONE_POSITION = "위치를 입력하세요: "

        private const val ERROR_INVALID_LENGTH = "입력값이 너무 짧습니다"
    }
}
