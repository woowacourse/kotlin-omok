package woowacourse.omok.domain

import woowacourse.omok.adapter.RuleResult
import woowacourse.omok.domain.model.Board
import woowacourse.omok.domain.model.rule.OmokRule
import woowacourse.omok.domain.model.state.OmokState
import woowacourse.omok.domain.model.state.Turn
import woowacourse.omok.domain.model.stone.Stone
import woowacourse.omok.domain.model.stone.Stones
import woowacourse.omok.domain.repository.StoneRepository
import woowacourse.ui.GameEvent
import woowacourse.ui.PlayEvent

class Game(
    private val rule: OmokRule,
    private val stoneRepository: StoneRepository,
    private val gameEvent: GameEvent,
    board: Board,
) {
    private var state: OmokState = Turn(stoneRepository.lastStoneType(board).reverse())
    private var stones: Stones = stoneRepository.allInBoardSize(board)

    fun initBoard() = gameEvent.initBoard(stones)

    fun play(playEvent: PlayEvent) {
        val position = playEvent.onPosition()
        val stone = Stone(position, state.stoneType)
        val ruleResult = rule.canPlace(stones, stone)
        playEvent.showPlaceResult(ruleResult)
        if (ruleResult !is RuleResult.OnRule) return
        placeStone(stone)
        playEvent.onPlace(stone)
        if (state.isFinished()) playEvent.onFinish(state.stoneType, ::resetGame)
    }

    fun isFinished() = state.isFinished()

    private fun resetGame() {
        state = state.play()
        stoneRepository.clear()
        stones = Stones(listOf())
    }

    private fun placeStone(stone: Stone) {
        stoneRepository.insert(stone)
        stones += stone
        state = if (rule.checkWin(stones, stone)) state.finish() else state.play()
    }
}
