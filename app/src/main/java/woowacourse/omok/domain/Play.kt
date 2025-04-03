package woowacourse.omok.domain

import woowacourse.omok.adapter.RuleResult
import woowacourse.omok.domain.event.GameEvent
import woowacourse.omok.domain.event.PlayEvent
import woowacourse.omok.domain.model.Board
import woowacourse.omok.domain.model.rule.OmokRule
import woowacourse.omok.domain.model.state.OmokState
import woowacourse.omok.domain.model.state.Turn
import woowacourse.omok.domain.model.stone.Stone
import woowacourse.omok.domain.model.stone.Stones
import woowacourse.omok.domain.repository.StoneRepository

class Play(
    private val rule: OmokRule,
    private val stoneRepository: StoneRepository,
    private val gameId: Long,
    private val board: Board,
    gameEvent: GameEvent,
) {
    private var state: OmokState = Turn(stoneRepository.lastStoneType(gameId, board).reverse())
    private var stones: Stones = stoneRepository.allInBoardSize(gameId, board)

    init {
        gameEvent.initBoard(this)
        gameEvent.showInitStones(stones)
    }

    fun play(playEvent: PlayEvent) {
        val position = playEvent.onPosition(board)
        val stone = Stone(position, state.stoneType)
        val ruleResult = rule.canPlace(stones, stone)
        playEvent.showPlaceResult(ruleResult)
        if (ruleResult !is RuleResult.OnRule) return
        placeStone(stone)
        playEvent.onPlace(stone)
        if (isFinished()) {
            stoneRepository.clear(gameId)
            playEvent.onFinish(state.stoneType, ::resetGame)
        }
    }

    fun isFinished() = state.isFinished()

    private fun resetGame() {
        state = state.play()
        stones = Stones(listOf())
    }

    private fun placeStone(stone: Stone) {
        stoneRepository.insert(gameId, stone)
        stones += stone
        state = if (rule.checkWin(stones, stone)) state.finish() else state.play()
    }
}
