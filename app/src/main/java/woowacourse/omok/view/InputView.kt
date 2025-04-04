package woowacourse.omok.view

import woowacourse.omok.model.Col
import woowacourse.omok.model.Position
import woowacourse.omok.model.Row
import woowacourse.omok.model.StoneColor
import woowacourse.omok.view.Message.ERROR_FORMAT
import woowacourse.omok.view.Message.INPUT_MESSAGE_GUIDE

interface InputView {

    var inputListener: ((position: String) -> Unit)?

    fun onCellClicked(position: String) {
        inputListener?.invoke(position)
    }

    private fun validReadln(): String {
        val input = readln()
        when {
            input.length < 2 -> printError(Message.ERROR_INPUT)
            input.substring(1).toIntOrNull() == null -> printError(Message.ERROR_INPUT)
            else -> return input
        }
        return errorReInput()
    }

    fun errorReInput(): String {
        print(INPUT_MESSAGE_GUIDE)
        return validReadln()
    }

    fun printError(message: String) {
        print(ERROR_FORMAT)
        print(message)
    }

    fun StoneColor.toDisplay(): String =
        when (this) {
            StoneColor.BLACK -> "흑"
            StoneColor.WHITE -> "백"
        }

    private fun Position.toDisplay(): String = "${this.col.toDisplay()}${this.row.toDisplay()}"

    private fun Row.toDisplay(): Int = this.value

    private fun Col.toDisplay(): Char = (this.value + 64).toChar()

    companion object {
    }
}
