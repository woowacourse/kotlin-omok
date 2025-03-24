package omok.adapter

import omok.domain.model.position.Stone
import omok.domain.model.rule.RenjuRule
import omok.domain.model.stone.StoneType
import omok.domain.model.stone.Stones
import rule.OmokRule
import rule.wrapper.point.Point

class RenjuRuleAdapter(private val omokRule: OmokRule) : RenjuRule {
    override fun checkWin(
        stones: Stones,
        stone: Stone,
    ): Boolean {
        val blackPoints = stones.typeStones(StoneType.BLACK).map { it.toPoint() }
        val whitePoints = stones.typeStones(StoneType.WHITE).map { it.toPoint() }
        val startPoint = stone.toPoint()
        return omokRule.checkWin(blackPoints, whitePoints, startPoint)
    }

    override fun canPlace(
        stones: Stones,
        stone: Stone,
    ): Boolean {
        if (stones.hasStone(stone)) return false
        return when (stone.stoneType) {
            StoneType.BLACK -> isPlaceRenjuRule(stones, stone)
            StoneType.WHITE -> true
        }
    }

    private fun isPlaceRenjuRule(
        stones: Stones,
        stone: Stone,
    ): Boolean {
        val blackPoints = stones.typeStones(StoneType.BLACK).map { it.toPoint() }
        val whitePoints = stones.typeStones(StoneType.WHITE).map { it.toPoint() }
        val startPoint = stone.toPoint()
        return omokRule.checkAnyFoulCondition(blackPoints, whitePoints, startPoint).state.not()
    }

    private fun Stone.toPoint() = Point(this.position.row.value, this.position.column.value)
}
