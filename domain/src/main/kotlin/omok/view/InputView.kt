package omok.view

import omok.domain.board.Position
import omok.domain.player.Stone
import omok.view.model.ColumnModel
import omok.view.model.RowModel
import omok.view.model.toPresentation

class InputView {
    fun readPosition(turn: Stone, position: Position? = null): Pair<Int, Int> {
        print(
            """
            |
            |${turn.toPresentation()}의 차례입니다. ${getLastPositionMessage(position)}
            |위치를 입력하세요: 
        """.trimMargin()
        )
        return getPositionAxis(position, turn)
    }

    private fun getPositionAxis(position: Position?, turn: Stone): Pair<Int, Int> {
        val input = readln().ifBlank { return readPosition(turn, position) }
        val column = ColumnModel.values().find { it.text == input.substring(0, 1) }?.axis
        val row = RowModel.values().find { it.text == input.substring(1) }?.axis

        return if (column != null && row != null) {
            Pair(column, row)
        } else {
            println("[ERROR] $input 라는 위치는 존재하지 않습니다.")
            readPosition(turn, position)
        }
    }

    private fun getLastPositionMessage(position: Position?): String {
        if (position == null) return ""
        return "(마지막 돌의 위치: ${position.column.toPresentation()}${position.row.toPresentation()})"
    }
}
