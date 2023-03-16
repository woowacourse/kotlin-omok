package omok.view

import omok.domain.Turn
import omok.domain.board.Position
import omok.model.toPresentation

class InputView {
    fun readPosition(position: Position?, turn: Turn): String? {
        print(
            """
            |
            |${turn.now.toPresentation()}의 차례입니다. ${getLastPositionMessage(position)}
            |위치를 입력하세요: 
        """.trimMargin()
        )
        return readln().ifBlank { null }
    }

    private fun getLastPositionMessage(position: Position?): String {
        if (position == null) return ""
        return "(마지막 돌의 위치: ${position.column.toPresentation()}${position.row.toPresentation()})"
    }
}
