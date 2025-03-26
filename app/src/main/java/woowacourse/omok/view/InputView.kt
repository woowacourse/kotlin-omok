package omok.view

import omok.model.board.Board

class InputView {
    fun inputStone(board: Board): Pair<Int, Int> {
        print(INPUT_STONE_POSITION)
        val input = readlnOrNull()?.trim().toString()
        return parse(input, board)
    }

    private fun parse(
        input: String,
        board: Board,
    ): Pair<Int, Int> {
        if (input.length < MIN_USER_INPUT) throw IllegalArgumentException(ERROR_INVALID_COORDINATE_FORMAT)

        val colChar = input[COL_CHAR_INDEX].uppercaseChar()
        require(colChar in MIN_COL_CHAR..<MIN_COL_CHAR + board.getWidth()) { ERROR_COL_STRING }
        val col = colChar - MIN_COL_CHAR

        val rowPart = input.substring(ROW_NUM_START_INDEX)
        val row = rowPart.toIntOrNull() ?: throw IllegalArgumentException(ERROR_ROW_NUM)
        require(row in MIN_ROW_NUM..board.getWidth()) { ERROR_ROW_NUM }

        val rowIndex = row - ROW_NUM_START_INDEX

        return rowIndex to col
    }

    companion object {
        private const val INPUT_STONE_POSITION = "위치를 입력하세요: "
        private const val MIN_COL_CHAR = 'A'
        private const val MIN_ROW_NUM = 0
        private const val MIN_USER_INPUT = 2
        private const val COL_CHAR_INDEX = 0
        private const val ROW_NUM_START_INDEX = 1

        private const val ERROR_INVALID_COORDINATE_FORMAT = "좌표 형식이 올바르지 않습니다"
        private const val ERROR_ROW_NUM = "행 번호가 유효하지 않습니다"
        private const val ERROR_COL_STRING = "열 문자가 유효하지 않습니다"
    }
}
