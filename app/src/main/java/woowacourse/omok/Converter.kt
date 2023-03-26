package woowacourse.omok

import domain.Board
import domain.Color
import domain.Position

object Converter {
    fun colorToString(color: Color): String {
        return when (color) {
            Color.BLACK -> "흑"
            Color.WHITE -> "백"
        }
    }

    fun indexToPosition(index: Int): Position {
        val boardSize = Board.getSize()
        val x = index / boardSize
        val y = index % boardSize
        return Position(x, y)
    }

    fun positionToIndex(position: Position): Int {
        val boardSize = Board.getSize()
        return position.x * boardSize + position.y
    }
}
