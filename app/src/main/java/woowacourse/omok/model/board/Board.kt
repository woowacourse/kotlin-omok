package woowacourse.omok.model.board

import omok.model.stone.position.Position
import woowacourse.omok.model.stone.StoneColor

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

    fun hasStoneAt(position: Position): Boolean = _stonesMap.containsKey(position)

    fun placeStone(
        position: Position,
        stoneColor: StoneColor,
    ): Board {
        val newBoard = Board(dimensions)
        newBoard._stonesMap.putAll(this._stonesMap)
        newBoard._stonesMap[position] = stoneColor
        return newBoard
    }

    companion object {
        fun initBoard(dimensions: BoardDimensions): Board = Board(dimensions)
    }
}
