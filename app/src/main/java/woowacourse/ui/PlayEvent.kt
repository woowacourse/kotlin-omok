package woowacourse.ui

import woowacourse.omok.adapter.RuleResult
import woowacourse.omok.domain.model.position.Position
import woowacourse.omok.domain.model.position.Stone
import woowacourse.omok.domain.model.stone.StoneType

interface PlayEvent {
    fun showPlaceResult(ruleResult: RuleResult)

    fun onPosition(): Position

    fun onPlace(stone: Stone)

    fun onFinish(
        stoneType: StoneType,
        resetGame: () -> Unit,
    )
}
