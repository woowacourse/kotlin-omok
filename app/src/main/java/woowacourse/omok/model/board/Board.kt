package woowacourse.omok.model.board

import android.os.Build
import woowacourse.omok.model.stone.Stone
import woowacourse.omok.model.stone.StoneColor
import woowacourse.omok.model.stone.position.Position

class Board(
    val boardSize: BoardSize = BoardSize(),
    stonesMap: LinkedHashMap<Position, StoneColor> = LinkedHashMap(),
) {
    private val _stonesMap: LinkedHashMap<Position, StoneColor> = LinkedHashMap(stonesMap)

    val stonesMap: LinkedHashMap<Position, StoneColor>
        get() = LinkedHashMap(_stonesMap)

    val lastStone: Stone?
        get() {
            return if (Build.VERSION.SDK_INT >= ANDROID_15_SDK_INT) {
                _stonesMap.lastEntry()?.let { Stone(it.key, it.value) }
            } else {
                _stonesMap.entries.lastOrNull()?.let { Stone(it.key, it.value) }
            }
        }

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
            stonesMap.containsKey(investigatedPosition) -> PositionStatus.PLACED
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

        private const val ANDROID_15_SDK_INT = 35
    }
}
