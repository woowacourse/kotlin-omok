package woowacourse.omok.domain

import woowacourse.omok.adapter.RuleResult
import woowacourse.omok.domain.model.rule.OmokRule
import woowacourse.omok.domain.model.state.OmokState
import woowacourse.omok.domain.model.state.Turn
import woowacourse.omok.domain.model.stone.Stone
import woowacourse.omok.domain.model.stone.StoneType
import woowacourse.omok.domain.model.stone.Stones
import woowacourse.ui.PlayEvent

class Game(
    private val rule: OmokRule,
    private var stones: Stones = Stones(listOf()),
    private var state: OmokState = Turn(StoneType.BLACK),
) {
    fun play(playEvent: PlayEvent) {
        val position = playEvent.onPosition()
        val stone = Stone(position, state.stoneType)
        val ruleResult = rule.canPlace(stones, stone)
        playEvent.showPlaceResult(rule.canPlace(stones, stone))
        if (ruleResult !is RuleResult.OnRule) return
        placeStone(stone)
        playEvent.onPlace(stone)
        if (state.isFinished()) playEvent.onFinish(state.stoneType, ::resetGame)
    }

    fun isFinished() = state.isFinished()

    private fun resetGame() {
        state = state.turn()
        stones = Stones(listOf())
    }

    private fun placeStone(stone: Stone) {
        stones += stone
        state = if (rule.checkWin(stones, stone)) state.finish() else state.turn()
    }
}
