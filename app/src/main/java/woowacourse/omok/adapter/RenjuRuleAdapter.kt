package woowacourse.omok.adapter

import rule.OmokRule
import rule.wrapper.point.Point
import woowacourse.omok.domain.model.position.Stone
import woowacourse.omok.domain.model.rule.PlaceResult
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
        return omokRule.checkWin(blackPoints, whitePoints, startPoint)
    }

    override fun canPlace(
        stones: Stones,
        stone: Stone,
    ): PlaceResult {
        if (stones.hasStone(stone)) return PlaceResult.DuplicatePosition("이미 둔 곳은 둘 수 없습니다.")
        return when (stone.stoneType) {
            StoneType.BLACK -> placeRenjuRule(stones, stone)
            StoneType.WHITE -> PlaceResult.OnPlace(stones)
        }
    }

    private fun placeRenjuRule(
        stones: Stones,
        stone: Stone,
    ): PlaceResult {
        val blackPoints = stones.typeStones(StoneType.BLACK).map { it.toPoint() }
        val whitePoints = stones.typeStones(StoneType.WHITE).map { it.toPoint() }
        val startPoint = stone.toPoint()
        if (omokRule.checkAnyFoulCondition(
                blackPoints,
                whitePoints,
                startPoint,
            ).state
        ) {
            return PlaceResult.RenJuRule("렌즈룰에 포함되는 위치 입니다.")
        }
        return PlaceResult.OnPlace(stones)
    }

    private fun Stone.toPoint() = Point(this.position.row.value, this.position.column.value)
}
