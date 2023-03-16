package view

import domain.Coordinate
import domain.Position

object InputView {
    fun inputPosition(): Position {
        val input = readln().trim()
        if (input.isBlank()) return inputPosition()
        val x: Int = convertX(input.first().toString().uppercase()) ?: return inputPosition()
        val y: Int = input.substring(1, input.length).toIntOrNull() ?: return inputPosition()
        return Position(Coordinate(15 - y), Coordinate(x))
    }

    private fun convertX(x: String): Int? {
        return AlphabetCoordinate.convertX(x)
    }
}
