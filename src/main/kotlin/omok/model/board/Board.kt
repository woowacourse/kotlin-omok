package omok.model.board

import omok.model.rule.OmokRule
import omok.model.stone.Stone
import omok.model.stone.StoneState
import omok.model.stone.position.Position
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
            lastStone?.let { return omokRule.isLastStoneOmok(stonesMap, lastStone) }
            return false
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

    private val blackPoints: List<rule.wrapper.point.Point>
        get() {
            return stonesMap
                .filter { (_, stoneState) ->
                    stoneState == StoneState.BLACK
                }.map { (position, _) ->
                    position.toPoint()
                }
        }

    private val whitePoints: List<rule.wrapper.point.Point>
        get() {
            return stonesMap
                .filter { (_, stoneState) ->
                    stoneState == StoneState.WHITE
                }.map { (position, _) ->
                    position.toPoint()
                }
        }

    fun placeStone(nextPosition: Position): Board {
        val nextStone = Stone(nextPosition, nextStoneState)
        require(!stonesMap.containsKey(nextStone.position)) { ERROR_STONE_ALREADY_EXITS }
        require(lastStone == null || (nextStone.stoneState != (lastStone.stoneState))) { ERROR_SUCCESSION_SAME_STATE_STONE }

        val newBoardStones = stonesMap + (nextStone.position to nextStone.stoneState)

        if (nextStoneState == StoneState.BLACK) {
            val violationType = blackRenjuRule.checkAnyFoulCondition(blackPoints, whitePoints, nextPosition.toPoint())
            when (violationType) {
                Violation.DOUBLE_THREE -> throw Exception(ERROR_DOUBLE_THREE)
                Violation.DOUBLE_FOUR -> throw Exception(ERROR_DOUBLE_FOUR)
                Violation.OVERLINE -> throw Exception(ERROR_OVERLINE)
                Violation.NONE -> {}
            }
        }

        return Board(newBoardStones, nextStone)
    }

    companion object {
        private const val BOARD_SIZE = 15

        private const val ERROR_STONE_ALREADY_EXITS = "해당하는 위치에 돌이 존재합니다"
        private const val ERROR_SUCCESSION_SAME_STATE_STONE = "같은 색의 돌을 연속하여 착수할 수 없습니다"
        private const val ERROR_DOUBLE_THREE = "3-3 반칙이 발생했습니다"
        private const val ERROR_DOUBLE_FOUR = "4-4 반칙이 발생했습니다"
        private const val ERROR_OVERLINE = "장목 반칙이 발생했습니다"

        fun initBoard(): Board {
            val initStonesMap: Map<Position, StoneState> = emptyMap()
            return Board(initStonesMap)
        }
    }
}
