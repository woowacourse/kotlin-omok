package omok.model.board

import omok.model.stone.StoneColor
import omok.model.stone.position.Position

class Board private constructor(
    val stonesMap: Map<Position, StoneColor> = emptyMap(),
) {
    fun positionAt(
        position: Position,
        stoneColor: StoneColor,
    ): Board {
        require(!stonesMap.containsKey(position)) { ERROR_STONE_ALREADY_EXITS }
        return Board(stonesMap + (position to stoneColor))
    }

    companion object {
        private const val ERROR_STONE_ALREADY_EXITS = "해당하는 위치에 돌이 존재합니다"

        fun initBoard(): Board {
            val initStonesMap: Map<Position, StoneColor> = emptyMap()
            return Board(initStonesMap)
        }
    }
}
