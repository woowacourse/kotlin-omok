package omok.model.board

import omok.model.stone.StoneColor
import omok.model.stone.position.Position

class Board private constructor(
    val stonesMap: Map<Position, StoneColor> = emptyMap(),
    private val dimensions: BoardDimensions,
) {
    fun getWidth() = dimensions.width

    fun getHeight() = dimensions.height

    fun getBlackStones() = stonesMap.filter { it.value == StoneColor.BLACK }.map { it.key }

    fun getWhiteStones() = stonesMap.filter { it.value == StoneColor.WHITE }.map { it.key }

    fun positionAt(
        position: Position,
        stoneColor: StoneColor,
    ): Board {
        require(!stonesMap.containsKey(position)) { ERROR_STONE_ALREADY_EXITS }
        return Board(stonesMap + (position to stoneColor), dimensions)
    }

    companion object {
        private const val ERROR_STONE_ALREADY_EXITS = "해당하는 위치에 돌이 존재합니다"

        fun initBoard(dimensions: BoardDimensions): Board {
            val initStonesMap: Map<Position, StoneColor> = emptyMap()
            return Board(initStonesMap, dimensions)
        }
    }
}
