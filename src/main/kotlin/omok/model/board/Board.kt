package omok.model.board

import omok.model.stone.Stone
import omok.model.stone.StoneState
import omok.model.stone.position.Position

class Board(
    val boardSize: BoardSize = BoardSize(15),
    stonesMap: LinkedHashMap<Position, StoneState> = LinkedHashMap(),
) {
    private val _stonesMap: LinkedHashMap<Position, StoneState> = LinkedHashMap(stonesMap)

    val stonesMap: LinkedHashMap<Position, StoneState>
        get() = LinkedHashMap(_stonesMap)

    val lastStone: Stone?
        get() = _stonesMap.lastEntry()?.let { Stone(it.key, it.value) }

    val nextStoneState: StoneState
        get() {
            lastStone?.let { stone ->
                return when (stone.stoneState) {
                    StoneState.BLACK -> StoneState.WHITE
                    StoneState.WHITE -> StoneState.BLACK
                    StoneState.NONE -> StoneState.NONE
                }
            } ?: run {
                return StoneState.BLACK
            }
        }

    fun placeStone(nextPosition: Position): Board {
        val nextStone = Stone(nextPosition, nextStoneState)
        require(!stonesMap.containsKey(nextStone.position)) { ERROR_STONE_ALREADY_EXITS }
        require(lastStone?.stoneState != nextStone.stoneState) {
            ERROR_SUCCESSION_SAME_STATE_STONE
        }

        val newStonesMap = stonesMap
        newStonesMap[nextStone.position] = nextStone.stoneState
        return Board(boardSize, newStonesMap)
    }

    companion object {
        private const val ERROR_STONE_ALREADY_EXITS = "해당하는 위치에 돌이 존재합니다"
        private const val ERROR_SUCCESSION_SAME_STATE_STONE = "같은 색의 돌을 연속하여 착수할 수 없습니다"
    }
}
