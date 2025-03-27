package woowacourse.omok.domain

import woowacourse.omok.adapter.RuleResult
import woowacourse.omok.domain.model.position.Position
import woowacourse.omok.domain.model.position.Stone
import woowacourse.omok.domain.model.rule.OmokRule
import woowacourse.omok.domain.model.state.OmokState
import woowacourse.omok.domain.model.state.Turn
import woowacourse.omok.domain.model.stone.StoneType
import woowacourse.omok.domain.model.stone.Stones

class Game(
    private val rule: OmokRule,
    private var stones: Stones = Stones(listOf()),
    private var state: OmokState = Turn(StoneType.BLACK),
) {
    val currentStoneType get() = state.stoneType

    fun canPlace(position: Position): RuleResult {
        val stone = Stone(position, state.stoneType)
        return rule.canPlace(stones, stone)
    }

    fun placeStone(position: Position) {
        val stone = Stone(position, state.stoneType)
        stones += stone
        state = if (rule.checkWin(stones, stone)) state.finish() else state.turn()
    }

    fun isFinished() = state.isFinished()
}
