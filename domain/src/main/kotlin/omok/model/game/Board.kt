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

    fun addStone(goStone: GoStone) {
        lastPlacedStone = goStone
        _board[goStone.coordinate.y][goStone.coordinate.x ] = lastPlacedStone
    }

    fun canAdd(coordinate: Coordinate): Boolean {
        if (_board[coordinate.y][coordinate.x] == null) return true
        else throw IllegalArgumentException("해당 위치에 이미 바둑돌이 있습니다.")
    }

    companion object {
        const val BOARD_LENGTH = 15
    }
}
