package omok.model.game

import omok.model.stone.Coordinate
import omok.model.stone.GoStone
import omok.model.stone.GoStoneColor

class Board {
    private val _board: List<MutableList<GoStone?>> =
        List(BOARD_LENGTH) { MutableList(BOARD_LENGTH) { null } }
    val board: List<List<GoStone?>>
        get() = _board.map { it.toList() }

    var lastPlacedStone: GoStone = GoStone.EMPTY
        private set

    fun getNextColor(): GoStoneColor = GoStoneColor.getNextColor(lastPlacedStone.color)

    fun addStone(goStone: GoStone) {
        lastPlacedStone = goStone
        _board[goStone.coordinate.y][goStone.coordinate.x ] = lastPlacedStone
    }

    fun canAdd(coordinate: Coordinate): Boolean {
        return _board[coordinate.y][coordinate.x] == null
    }

    fun addAllStones(stones: List<GoStone>) {
        stones.forEach {
            addStone(it)
        }
    }

    companion object {
        const val BOARD_LENGTH = 15
    }
}
