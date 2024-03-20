package omok.view

import omok.model.Color
import omok.model.Position
import omok.model.Stone

class InputView {
    fun inputPosition(lastTurn: Stone?): Pair<Int, Char> {
        lastTurn?.let {
            val currentColor = Color.getReversedColor(it.color)
            println("%s의 차례입니다.".format(currentColor.label))
            showLastPosition(lastTurn.position)
        } ?: println("%s의 차례입니다.".format(Color.BLACK.label))
        print("위치를 입력하세요: ")
        val input = readln()
        println()
        val col = input[0]
        val row = input.slice(1 until input.length).toInt()
        return Pair(row, col)
    }

    private fun showLastPosition(position: Position) {
        println("(마지막 돌의 위치: %s%s)".format(position.col.title, position.row.title))
    }
}
