package woowacourse.omok.view

import woowacourse.omok.model.board.Board
import woowacourse.omok.model.rule.CoordinateError
import woowacourse.omok.model.rule.CoordinateResult

class InputView {
    fun inputStone(board: Board): CoordinateResult {
        print(INPUT_STONE_POSITION)
        val input = readlnOrNull()?.trim().toString()
        return parse(input, board)
    }

    private fun parse(
        input: String,
        board: Board,
    ): CoordinateResult {
        if (input.length < MIN_USER_INPUT) {
            return CoordinateResult.Failure(CoordinateError.InvalidCoordinateFormat)
        }

        val dimensions = board.dimensions
        val colChar = input[COL_CHAR_INDEX].uppercaseChar()
        if (colChar !in MIN_COL_CHAR until (MIN_COL_CHAR + dimensions.width)) {
            return CoordinateResult.Failure(CoordinateError.InvalidColString)
        }
        val col = colChar - MIN_COL_CHAR

        val rowPart = input.substring(ROW_NUM_START_INDEX)
        val row =
            rowPart.toIntOrNull()
                ?: return CoordinateResult.Failure(CoordinateError.InvalidRowNumber)
        if (row !in MIN_ROW_NUM + 1..dimensions.height) {
            return CoordinateResult.Failure(CoordinateError.InvalidRowNumber)
        }

        val rowIndex = row - 1

        return CoordinateResult.Success(rowIndex, col)
    }

    companion object {
        private const val INPUT_STONE_POSITION = "위치를 입력하세요: "
        private const val MIN_COL_CHAR = 'A'
        private const val MIN_ROW_NUM = 0
        private const val MIN_USER_INPUT = 2
        private const val COL_CHAR_INDEX = 0
        private const val ROW_NUM_START_INDEX = 1
    }
}
