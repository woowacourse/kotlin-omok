package woowacourse.omok.adapter

import rule.OmokRule
import rule.wrapper.point.Point
import woowacourse.omok.domain.model.position.Stone
import woowacourse.omok.domain.model.rule.RenjuRule
import woowacourse.omok.domain.model.stone.StoneType
import woowacourse.omok.domain.model.stone.Stones

class RenjuRuleAdapter(private val omokRule: OmokRule) : RenjuRule {
    override fun checkWin(
        stones: Stones,
        stone: Stone,
    ): Boolean {
        val blackPoints = stones.typeStones(StoneType.BLACK).map { it.toPoint() }
        val whitePoints = stones.typeStones(StoneType.WHITE).map { it.toPoint() }
        val startPoint = stone.toPoint()
        return when (stone.stoneType) {
            StoneType.BLACK -> omokRule.checkWin(blackPoints, whitePoints, startPoint)
            StoneType.WHITE -> omokRule.checkWin(whitePoints, blackPoints, startPoint)
        }
    }

    override fun canPlace(
        stones: Stones,
        stone: Stone,
    ): RuleResult {
        if (stones.hasStone(stone)) return RuleResult.DuplicatePosition
        return when (stone.stoneType) {
            StoneType.BLACK -> placeRenjuRule(stones, stone)
            StoneType.WHITE -> RuleResult.OnRule(stone)
        }
    }

    private fun placeRenjuRule(
        stones: Stones,
        stone: Stone,
    ): RuleResult {
        val blackPoints = stones.typeStones(StoneType.BLACK).map { it.toPoint() }
        val whitePoints = stones.typeStones(StoneType.WHITE).map { it.toPoint() }
        val startPoint = stone.toPoint()
        if (omokRule.checkAnyFoulCondition(
                blackPoints,
                whitePoints,
                startPoint,
            ).state
        ) {
            return RuleResult.RenJuRule
        }
        return RuleResult.OnRule(stone)
    }

    private fun Stone.toPoint() = Point(this.position.row.value, this.position.column.value)
}
