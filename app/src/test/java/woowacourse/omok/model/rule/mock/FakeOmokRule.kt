package woowacourse.omok.model.rule.mock

import woowacourse.omok.model.rule.OmokRule
import woowacourse.omok.model.rule.RenjuFoul
import woowacourse.omok.model.stone.Stone
import woowacourse.omok.model.stone.StoneColor
import woowacourse.omok.model.stone.position.Position

class FakeOmokRule : OmokRule {
    override fun checkLastBlackStoneFoul(
        stonesMap: Map<Position, StoneColor>,
        lastStone: Stone,
    ): RenjuFoul = RenjuFoul.THREE_BY_THREE_FOUL

    override fun isOmok(
        stonesMap: Map<Position, StoneColor>,
        lastStone: Stone,
    ): Boolean = true
}
