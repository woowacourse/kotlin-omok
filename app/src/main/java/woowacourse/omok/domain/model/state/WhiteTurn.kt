package woowacourse.omok.domain.model.state

import woowacourse.omok.domain.model.Position
import woowacourse.omok.domain.model.Stone
import woowacourse.omok.domain.model.StonePosition
import woowacourse.omok.domain.model.rule.RuleAdapter

class WhiteTurn(latestStonePosition: StonePosition, rule: RuleAdapter) : Turn(latestStonePosition, rule) {
    override val stone: Stone
        get() = Stone.WHITE

    override fun nextTurn(
        position: Position,
        rule: RuleAdapter,
    ): GameState = BlackTurn(latestStonePosition = StonePosition(position, stone), rule)
}
