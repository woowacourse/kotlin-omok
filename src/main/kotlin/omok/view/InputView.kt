package omok.view

import omok.model.Position
import omok.model.Stone

class InputView {
    fun readStonePosition(stone: Stone, recentPosition: Position?): Position {
        println("${stone.output()}의 차례입니다.")
        recentPosition?.run { println("마지막 돌의 위치: ${this.output()}") }
        print("위치를 입력하세요: ")
        return readln().toPosition()
    }
}

private fun Stone.output(): String = if (this == Stone.BLACK) "흑" else "백"

private fun Position.output(): String {
    val colName = (this.col + 'A'.code).toChar()
    val rowName = Position.MAX_INDEX - this.row + 1
    return "$colName$rowName"
}

private fun String.toPosition(): Position {
    require(substring(1, length).toIntOrNull() in 1..15) { "올바르지 않은 행입니다." }
    require(this[0] in 'A'..'O') { "올바르지 않은 열입니다." }

    val row = Position.MAX_INDEX - substring(1, length).toInt() + 1
    val col = this[0] - 'A'
    return Position(row, col)
}
