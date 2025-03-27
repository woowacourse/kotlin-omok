package view

import android.util.Log
import model.Col
import model.Position
import model.Row
import model.Stone
import model.StoneColor
import view.Message.ERROR_FORMAT
import view.Message.INPUT_MESSAGE_GUIDE
import view.Message.LAST_STONE_POSITION_MESSAGE
import view.Message.TURN_MESSAGE_FORMAT

interface InputView {

    var inputListener : ((position:String)->Unit)?

    fun onCellClicked(position:String){
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

    private fun StoneColor.toDisplay(): String =
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
