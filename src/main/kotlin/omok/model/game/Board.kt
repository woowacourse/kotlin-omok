package omok.model.game

import omok.model.stone.Coordinate
import omok.model.stone.GoStone
import omok.model.stone.GoStoneColor

class Board {
    private val _board: List<MutableList<GoStone?>> =
        List(15) { MutableList(15) { null } }
    val board: List<List<GoStone?>>
        get() = _board.map { it.toList() }

    var lastPlacedStone: GoStone? = null
        private set

    fun getNextColor(): GoStoneColor = GoStoneColor.getNextColor(lastPlacedStone?.color)

    fun addStone(color: GoStoneColor, coordinate: Coordinate) {
        lastPlacedStone = GoStone(color, coordinate)
        _board[coordinate.x - 1][coordinate.y - 1] = lastPlacedStone
    }

    fun canAdd(coordinate: Coordinate): Boolean {
        if (_board[coordinate.x - 1][coordinate.y - 1] == null) return true
        else throw IllegalArgumentException("해당 위치에 이미 바둑돌이 있습니다.")
    }

    companion object {
        const val BOARD_LENGTH = 15
    }
}
