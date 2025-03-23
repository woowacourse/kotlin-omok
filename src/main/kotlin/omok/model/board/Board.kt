package omok.model.board

import omok.external.mapper.toPoint
import omok.model.rule.OmokRule
import omok.model.stone.Stone
import omok.model.stone.StoneColor
import omok.model.stone.position.Position
import rule.BlackRenjuRule
import rule.type.Violation

class Board private constructor(
    val stonesMap: Map<Position, StoneColor> = emptyMap(),
    val lastStone: Stone? = null,
) {
    private val blackRenjuRule = BlackRenjuRule(BOARD_SIZE, BOARD_SIZE)
    private val omokRule = OmokRule(BOARD_SIZE)

    val isLastStoneOmok: Boolean
        get() {
            lastStone?.let { return omokRule.isLastStoneOmok(stonesMap, lastStone) }
            return false
        }

    val nextStoneColor: StoneColor
        get() {
            lastStone?.let {
                return when (lastStone.stoneColor) {
                    StoneColor.BLACK -> StoneColor.WHITE
                    StoneColor.WHITE -> StoneColor.BLACK
                }
            } ?: run {
                return StoneColor.BLACK
            }
        }

    private fun blackPositions(): List<Position> =
        stonesMap
            .filter { it.value == StoneColor.BLACK }
            .map { it.key }

    private fun whitePositions(): List<Position> =
        stonesMap
            .filter { it.value == StoneColor.WHITE }
            .map { it.key }

    fun placeStone(nextPosition: Position): Board {
        // 비어있는 상태면 place 할 수 있음
        require(!stonesMap.containsKey(nextPosition)) { ERROR_STONE_ALREADY_EXITS }
        val nextStone = Stone(nextPosition, nextStoneColor)

        require(lastStone == null || nextStone.stoneColor != lastStone.stoneColor) {
            ERROR_SUCCESSION_SAME_STATE_STONE
        }
        val updatedMap = stonesMap + (nextStone.position to nextStone.stoneColor)

        if (nextStoneColor == StoneColor.BLACK) {
            val violation =
                blackRenjuRule.checkAnyFoulCondition(
                    blackPositions().map { it.toPoint() },
                    whitePositions().map { it.toPoint() },
                    nextStone.position.toPoint(),
                )
            when (violation) {
                Violation.DOUBLE_THREE -> throw Exception(ERROR_DOUBLE_THREE)
                Violation.DOUBLE_FOUR -> throw Exception(ERROR_DOUBLE_FOUR)
                Violation.OVERLINE -> throw Exception(ERROR_OVERLINE)
                Violation.NONE -> {}
            }
        }

        return Board(updatedMap, nextStone)
    }

    companion object {
        private const val BOARD_SIZE = 1510000

        private const val ERROR_STONE_ALREADY_EXITS = "해당하는 위치에 돌이 존재합니다"
        private const val ERROR_SUCCESSION_SAME_STATE_STONE = "같은 색의 돌을 연속하여 착수할 수 없습니다"
        private const val ERROR_DOUBLE_THREE = "3-3 반칙이 발생했습니다"
        private const val ERROR_DOUBLE_FOUR = "4-4 반칙이 발생했습니다"
        private const val ERROR_OVERLINE = "장목 반칙이 발생했습니다"

        fun initBoard(): Board {
            val initStonesMap: Map<Position, StoneColor> = emptyMap()
            return Board(initStonesMap)
        }
    }
}
