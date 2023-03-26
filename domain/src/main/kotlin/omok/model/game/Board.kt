package omok.model.game

import omok.model.stone.Coordinate
import omok.model.stone.GoStone
import omok.model.stone.GoStoneColor

class Board(val sizeX: Int = DEFAULT_BOARD_X_SIZE, val sizeY: Int = DEFAULT_BOARD_Y_SIZE) {
    private val _board: List<MutableList<GoStone?>> = List(sizeY) { MutableList(sizeX) { null } }
    val board: List<List<GoStone?>>
        get() = _board.map { it.toList() }

    var lastPlacedStone: GoStone? = null
        private set

    fun getNextColor(): GoStoneColor = GoStoneColor.getNextColor(lastPlacedStone?.color)

    fun addStone(goStone: GoStone) {
        lastPlacedStone = goStone
        _board[goStone.coordinate.y - 1][goStone.coordinate.x - 1] = lastPlacedStone
    }

    fun canAdd(coordinate: Coordinate): Boolean {
        if (_board[coordinate.y - 1][coordinate.x - 1] == null) return true
        else throw IllegalArgumentException("해당 위치에 이미 바둑돌이 있습니다. x: ${coordinate.x}, y: ${coordinate.y}")
    }

    companion object {
        private const val DEFAULT_BOARD_X_SIZE = 15
        private const val DEFAULT_BOARD_Y_SIZE = 15
    }
}
