package omok.model.board

import omok.model.stone.StoneColor
import omok.model.stone.position.Position

class Board private constructor(
    private val dimensions: BoardDimensions,
) {
    private val _stonesMap: MutableMap<Position, StoneColor> = mutableMapOf()
    val stonesMap: Map<Position, StoneColor>
        get() = _stonesMap.toMap()

    fun getWidth() = dimensions.width

    fun getHeight() = dimensions.height

    fun getBlackStones() = stonesMap.filter { it.value == StoneColor.BLACK }.map { it.key }

    fun getWhiteStones() = stonesMap.filter { it.value == StoneColor.WHITE }.map { it.key }

    fun positionAt(
        position: Position,
        stoneColor: StoneColor,
    ): Board {
        require(!stonesMap.containsKey(position)) { ERROR_STONE_ALREADY_EXITS }
        val newBoard = Board(dimensions)
        newBoard._stonesMap.putAll(this._stonesMap)
        newBoard._stonesMap[position] = stoneColor
        return newBoard
    }

    companion object {
        private const val ERROR_STONE_ALREADY_EXITS = "해당하는 위치에 돌이 존재합니다"

        fun initBoard(dimensions: BoardDimensions): Board {
            val initStonesMap: Map<Position, StoneColor> = emptyMap()
            return Board(dimensions)
        }
    }
}
