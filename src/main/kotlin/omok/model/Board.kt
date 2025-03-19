package omok.model

import rule.BlackRenjuRule
import rule.OmokRule
import rule.WhiteRenjuRule
import rule.type.Violation

class Board private constructor(
    val stonesMap: Map<Position, StoneState> = emptyMap(),
    val lastStone: Stone? = null,
) {
    private val blackRenjuRule = BlackRenjuRule(15, 15)
    private val whiteRenjuRule = WhiteRenjuRule
    private val omokRule = OmokRule

    val blackPoints: List<rule.wrapper.point.Point>
        get() {
            return stonesMap
                .filter { (_, stoneState) ->
                    stoneState == StoneState.BLACK
                }.map { (position, _) ->
                    position.toPoint()
                }
        }

    val whitePoints: List<rule.wrapper.point.Point>
        get() {
            return stonesMap
                .filter { (_, stoneState) ->
                    stoneState == StoneState.WHITE
                }.map { (position, _) ->
                    position.toPoint()
                }
        }

    val nextStoneState: StoneState
        get() {
            lastStone?.let {
                return when (lastStone.stoneState) {
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
        require(!stonesMap.containsKey(nextStone.position)) { "해당하는 위치에 돌이 존재합니다" }
        require(lastStone == null || (nextStone.stoneState != (lastStone.stoneState))) { "같은 색의 돌을 연속하여 착수할 수 없습니다" }

        val newBoardStones = stonesMap + (nextStone.position to nextStone.stoneState)

        val violationType = blackRenjuRule.checkAnyFoulCondition(blackPoints, whitePoints, nextPosition.toPoint())
        when (violationType) {
            Violation.DOUBLE_THREE -> throw Exception("3-3 반칙이 발생했습니다.")
            Violation.DOUBLE_FOUR -> throw Exception("4-4 입니다")
            Violation.OVERLINE -> throw Exception("장목입니다")
            Violation.NONE -> blackRenjuRule.checkWin(blackPoints, whitePoints, nextPosition.toPoint())
        }

        return Board(newBoardStones, nextStone)
    }

    private fun stonePlacedState(position: Position): StoneState = stonesMap[position] ?: StoneState.NONE

    private fun countConnected(
        position: Position,
        stoneState: StoneState,
        direction: Direction,
    ): Int {
        var count = 0
        var x = position.row.value + direction.dx
        var y = position.col.value + direction.dy

        while (x in 0 until BOARD_SIZE && y in 0 until BOARD_SIZE) {
            val nextPos = Position(Row(x), Col(y))
            if (stonesMap[nextPos] == stoneState) {
                count++
                x += direction.dx
                y += direction.dy
            } else {
                break
            }
        }
        return count
    }

    fun isLastStoneOmok(): Boolean {
        lastStone?.let {
            val position = lastStone.position
            if (stonePlacedState(position) == StoneState.NONE) {
                return false
            }

            val stoneState = stonePlacedState(position)

            for (i in Direction.entries.indices step 2) { // (1,2)계산, (3,4)계산
                val dir1 = Direction.entries[i] // 정방향 1, 3
                val dir2 = Direction.entries[i + 1] // 반대방향 2, 4

                val count1 = countConnected(position, stoneState, dir1)
                val count2 = countConnected(position, stoneState, dir2)
                val totalCount = count1 + count2 + 1
                if (totalCount >= 5) return true
            }
            return false
        }
        return false
    }

    companion object {
        private const val BOARD_SIZE = 15

        fun initBoard(): Board {
            val initStonesMap: Map<Position, StoneState> = emptyMap()
            return Board(initStonesMap)
        }

        fun customBoard(stones: Collection<Stone>): Board {
            val board = mutableMapOf<Position, StoneState>()
            val lastStone = stones.last()
            stones.forEach { stone ->
                val row = stone.position.row.value
                val col = stone.position.col.value

                if (row in 0 until BOARD_SIZE && col in 0 until BOARD_SIZE) {
                    board[stone.position] = stone.stoneState
                }
            }

            return Board(board, lastStone)
        }
    }
}
