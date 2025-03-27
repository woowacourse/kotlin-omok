package woowacourse.omok.model.rule

import woowacourse.omok.model.stone.Stone
import woowacourse.omok.model.stone.StoneColor
import woowacourse.omok.model.stone.position.Position

interface OmokRule {
    fun checkLastBlackStoneFoul(
        stonesMap: Map<Position, StoneColor>,
        lastStone: Stone,
    ): RenjuFoul

    fun isOmok(
        stonesMap: Map<Position, StoneColor>,
        lastStone: Stone,
    ): Boolean

    class Fake : OmokRule {
        override fun checkLastBlackStoneFoul(
            stonesMap: Map<Position, StoneColor>,
            lastStone: Stone,
        ): RenjuFoul = RenjuFoul.THREE_BY_THREE_FOUL

        override fun isOmok(
            stonesMap: Map<Position, StoneColor>,
            lastStone: Stone,
        ): Boolean = true
    }
}
