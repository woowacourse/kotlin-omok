package woowacourse.omok.domain.model.rule

import woowacourse.omok.domain.model.position.Stone
import woowacourse.omok.domain.model.stone.Stones

class OmokRule(private val renjuRule: RenjuRule) : Rule {
    override fun checkWin(
        stones: Stones,
        stone: Stone,
    ): Boolean =
        renjuRule.checkWin(
            stones,
            stone,
        )

    override fun canPlace(
        stones: Stones,
        stone: Stone,
    ): PlaceResult = renjuRule.canPlace(stones, stone)
}
