package omok.model.board

import omok.model.stone.Stone
import omok.model.stone.StoneColor
import omok.model.stone.position.Position

class Board(
    val boardSize: BoardSize = BoardSize(15),
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
            } ?: run {
                return StoneColor.BLACK
            }
        }

    fun nextStonePlacedBoard(nextPosition: Position): Board {
        validPositionCheck(nextPosition)
        val newStonesMap = stonesMap
        val nextStone = Stone(nextPosition, nextStoneColor)
        newStonesMap[nextStone.position] = nextStone.stoneColor
        return Board(boardSize, newStonesMap)
    }

    private fun validPositionCheck(position: Position) {
        require(!stonesMap.containsKey(position)) { ERROR_STONE_ALREADY_EXITS }
        require(position.col.value < boardSize.value && position.row.value < boardSize.value) {
            ERROR_OUT_OF_RANGE_STONE
        }
    }

    companion object {
        private const val ERROR_STONE_ALREADY_EXITS = "해당하는 위치에 돌이 존재합니다"
        private const val ERROR_OUT_OF_RANGE_STONE = "돌이 보드의 범위를 벗어났습니다"
    }
}
