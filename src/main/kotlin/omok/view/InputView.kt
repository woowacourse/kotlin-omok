package omok.view

import omok.model.Color
import omok.model.Position
import omok.model.Stone

class InputView {
    fun inputPosition(stone: Stone?): Pair<Int?, Char> {
        stone?.let {
            println("%s의 차례입니다.".format(it.color.label))
            showLastPosition(stone.position)
        } ?: println("%s의 차례입니다.".format(Color.BLACK.label))
        print("위치를 입력하세요: ")
        val input = readln()
        val col = input[0]
        val row = input.slice(1 until input.length).toIntOrNull()
        return Pair(row, col)
    }

    private fun showLastPosition(position: Position)  {
        println("(마지막 돌의 위치: %s%s)".format(position.col.title, position.row.title))
    }
}
