package view

import domain.Position

object InputView {
    fun inputPosition(): Position {
        print("위치를 입력하세요: ")
        val input = readln().trim()
        if (input.isBlank()) return inputPosition()
        val x: Int = convertX(input.first().toString().uppercase()) ?: return inputPosition()
        val y: Int = input.substring(1, input.length).toIntOrNull() ?: return inputPosition()
        return Position(x, y)
    }

    private fun convertX(x: String): Int? {
        return AlphabetCoordinate.convertX(x)
    }
}
