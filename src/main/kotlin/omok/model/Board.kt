package omok.model

import rule.BlackRenjuRule
import rule.type.Violation

class Board private constructor(
    val stonesMap: Map<Position, StoneState> = emptyMap(),
    val lastStone: Stone? = null,
) {
    private val blackRenjuRule = BlackRenjuRule(BOARD_SIZE, BOARD_SIZE)
    private val omokRule = OmokRule(BOARD_SIZE)

    val isLastStoneOmok: Boolean
        get() {
            lastStone?.let { omokRule.isLastStoneOmok(stonesMap, lastStone) }
            return false
        }

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
            Violation.NONE -> {}
        }

        return Board(newBoardStones, nextStone)
    }

    companion object {
        private const val BOARD_SIZE = 15

        fun initBoard(): Board {
            val initStonesMap: Map<Position, StoneState> = emptyMap()
            return Board(initStonesMap)
        }
    }
}
