package woowacourse.omok.domain.model.state

import woowacourse.omok.domain.model.Position
import woowacourse.omok.domain.model.Stone
import woowacourse.omok.domain.model.StonePosition
import woowacourse.omok.domain.model.rule.RuleAdapter

class BlackTurn(latestStonePosition: StonePosition, rule: RuleAdapter) : Turn(latestStonePosition, rule) {
    override val stone: Stone
        get() = Stone.BLACK

    override fun nextTurn(
        position: Position,
        rule: RuleAdapter,
    ): GameState = WhiteTurn(latestStonePosition = StonePosition(position, stone), rule)
}
