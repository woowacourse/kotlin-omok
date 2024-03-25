package omok.view.input

import omok.model.entity.Point

class ConsoleInputView : InputView {
    override tailrec fun getStonePoint(): Point {
        print("위치를 입력하세요: ")
        val input = readln()
        val inputX = input[0]
        val isInputXValid = inputX in INPUT_X_SIZE_RANGE

        val inputY = input.substring(1).toIntOrNull() ?: return getStonePoint()
        val isInputYValid = inputY in INPUT_Y_SIZE_RANGE

        if (!(isInputXValid && isInputYValid)) {
            return getStonePoint()
        }

        val x = alphabetToInt(inputX)
        val y = inputY

        return Point(x, y)
    }

    private fun alphabetToInt(alphabet: Char): Int = alphabet - 'A' + 1

    companion object {
        private const val INPUT_Y_MIN_SIZE = 1
        private const val INPUT_Y_MAX_SIZE = 15
        private val INPUT_Y_SIZE_RANGE = INPUT_Y_MIN_SIZE..INPUT_Y_MAX_SIZE

        private const val INPUT_X_MIN_SIZE = 'A'
        private const val INPUT_X_MAX_SIZE = 'O'
        private val INPUT_X_SIZE_RANGE = INPUT_X_MIN_SIZE..INPUT_X_MAX_SIZE
    }
}
