package omok.model.board

import omok.model.stone.Stone
import omok.model.stone.StoneColor
import omok.model.stone.position.Position

class Board(
    val boardSize: BoardSize = BoardSize(),
    stonesMap: LinkedHashMap<Position, StoneColor> = LinkedHashMap(),
) {
    private val _stonesMap: LinkedHashMap<Position, StoneColor> = LinkedHashMap(stonesMap)

    val stonesMap: LinkedHashMap<Position, StoneColor>
        get() = LinkedHashMap(_stonesMap)

    val lastStone: Stone?
        get() = _stonesMap.lastEntry()?.let { Stone(it.key, it.value) }

    val nextStoneColor: StoneColor
        get() {
            lastStone?.let { stone ->
                return when (stone.stoneColor) {
                    StoneColor.BLACK -> StoneColor.WHITE
                    StoneColor.WHITE -> StoneColor.BLACK
                }
            } ?: return StoneColor.BLACK
        }

    fun positionStatus(investigatedPosition: Position): PositionStatus =
        when {
            stonesMap.containsKey(investigatedPosition) -> PositionStatus.STONE_ALREADY_EXITS
            investigatedPosition.col.value !in MINIMUM_BOARD_INDEX until boardSize.value -> PositionStatus.OUT_OF_RANGE
            investigatedPosition.row.value !in MINIMUM_BOARD_INDEX until boardSize.value -> PositionStatus.OUT_OF_RANGE
            else -> PositionStatus.EMPTY
        }

    fun nextStonePlacedBoard(nextPosition: Position): Board {
        val newStonesMap = stonesMap
        val nextStone = Stone(nextPosition, nextStoneColor)
        newStonesMap[nextStone.position] = nextStone.stoneColor
        return Board(boardSize, newStonesMap)
    }

    companion object {
        private const val MINIMUM_BOARD_INDEX = 0
    }
}
