package view

import domain.Board
import domain.CoordinateState

enum class BoardParts(val value: String) {
    LEFT_TOP("┌"),
    CENTER_TOP("┬"),
    RIGHT_TOP("┐"),
    LEFT_MIDDLE("├"),
    CENTER_MIDDLE("┼"),
    RIGHT_MIDDLE("┤"),
    LEFT_BOTTOM("└"),
    CENTER_BOTTOM("┴"),
    RIGHT_BOTTOM("┘"),
    GENERAL("─"),
    BLACK_STONE("⚆"),
    WHITE_STONE("⚈"), ;

    companion object {
        fun getPart(coordinateState: CoordinateState, x: Int, y: Int): BoardParts {
            val maxCoordinate = Board.BOARD_SIZE - 1
            return when {
                x == 0 && y == 0 -> LEFT_TOP
                x == maxCoordinate && y == 0 -> RIGHT_TOP
                x == 0 && y == maxCoordinate -> LEFT_BOTTOM
                x == maxCoordinate && y == maxCoordinate -> RIGHT_BOTTOM
                x == 0 -> LEFT_MIDDLE
                x == maxCoordinate -> RIGHT_MIDDLE
                y == 0 -> CENTER_TOP
                y == maxCoordinate -> CENTER_BOTTOM
                else -> CENTER_MIDDLE
            }
        }
    }
}
