package omok.view.input

import omok.model.entity.Point

class ConsoleInputView : InputView {
    override tailrec fun getStonePoint(): Point {
        print("위치를 입력하세요: ")
        val input = readln()
        val inputX = input[0]
        val isInputXValid = inputX in INPUTX_SIZE_RANGE

        val inputY = input.substring(1).toIntOrNull() ?: return getStonePoint()
        val isInputYValid = inputY in INPUTY_SIZE_RANGE

        if (!(isInputXValid && isInputYValid)) {
            return getStonePoint()
        }

        val x = alphabetToInt(inputX)
        val y = inputY

        return Point(x, y)
    }

    private fun alphabetToInt(alphabet: Char): Int = alphabet - 'A' + 1

    companion object {

        private const val INPUTY_MIN_SIZE = 1
        private const val INPUTY_MAX_SIZE = 15
        private val INPUTY_SIZE_RANGE = INPUTY_MIN_SIZE..INPUTY_MAX_SIZE

        private const val INPUTX_MIN_SIZE = 'A'
        private const val INPUTX_MAX_SIZE = 'O'
        private val INPUTX_SIZE_RANGE = INPUTX_MIN_SIZE..INPUTX_MAX_SIZE
    }
}
