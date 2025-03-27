package woowacourse.omok.view

class InputView {
    fun getPosition(): Pair<Int, Int> {
        print("위치를 입력하세요: ")
        val input = requireNotNull(readlnOrNull()) { INPUT_ERROR_MESSAGE }
        val column = convertToColumn(input[0])
        val row = requireNotNull(input.drop(1).toIntOrNull()) { ROW_ERROR_MESSAGE }
        return column to row
    }

    private fun convertToColumn(column: Char): Int {
        require(column in ALPHABETS) { COLUMN_ERROR_MESSAGE }
        return ALPHABETS.indexOf(column) + 1
    }

    companion object {
        private val ALPHABETS = ('A'..'Z')
        private const val INPUT_ERROR_MESSAGE = "[ERROR] 입력이 존재하지 않습니다."
        private const val COLUMN_ERROR_MESSAGE = "[ERROR] 열 좌표는 알파벳 대문자여야 합니다."
        private const val ROW_ERROR_MESSAGE = "[ERROR] 행 좌표는 숫자여야 합니다."
    }
}
