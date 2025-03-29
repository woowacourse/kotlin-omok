package woowacourse.omok.domain

import woowacourse.omok.adapter.RuleResult
import woowacourse.omok.domain.model.position.Stone
import woowacourse.omok.domain.model.rule.OmokRule
import woowacourse.omok.domain.model.state.OmokState
import woowacourse.omok.domain.model.state.Turn
import woowacourse.omok.domain.model.stone.StoneType
import woowacourse.omok.domain.model.stone.Stones
import woowacourse.ui.PlayEvent

class Game(
    private val rule: OmokRule,
    private var stones: Stones = Stones(listOf()),
    private var state: OmokState = Turn(StoneType.BLACK),
) {
    val currentStoneType get() = state.stoneType

    fun play(playEvent: PlayEvent) {
        val position = playEvent.onPosition()
        val stone = Stone(position, state.stoneType)
        val ruleResult = rule.canPlace(stones, stone)
        playEvent.showPlaceResult(rule.canPlace(stones, stone))
        if (ruleResult !is RuleResult.OnRule) return
        placeStone(stone)
        playEvent.onPlace(stone.stoneType)
    }

    fun isFinished() = state.isFinished()

    fun resetGame() {
        state = state.turn()
        stones = Stones(listOf())
    }

    private fun placeStone(stone: Stone) {
        stones += stone
        state = if (rule.checkWin(stones, stone)) state.finish() else state.turn()
    }
}
