package woowacourse.omok.domain

import woowacourse.omok.domain.model.Board
import woowacourse.omok.domain.model.position.Position
import woowacourse.omok.domain.model.rule.OmokRule
import woowacourse.omok.domain.model.state.BlackStoneTurn
import woowacourse.omok.domain.model.state.Finish
import woowacourse.omok.domain.model.state.OmokEvent
import woowacourse.omok.domain.model.state.OmokState
import woowacourse.omok.domain.model.state.OmokStateMachine
import woowacourse.omok.domain.model.state.WhiteStoneTurn
import woowacourse.omok.domain.model.stone.OmokStone
import woowacourse.omok.domain.model.stone.StoneType

class Game(
    private val rule: OmokRule,
    private val board: Board = Board(),
    private val omokStateMachine: OmokStateMachine = OmokStateMachine(),
) {
    fun play(
        position: Position,
        onPlace: (StoneType) -> Unit,
        onFailure: (String) -> Unit,
        onFinish: (OmokState) -> Unit,
    ) {
        runCatching {
            process(position, onPlace)
        }.onFailure {
            onFailure(it.message ?: it.stackTraceToString())
        }

        if (omokStateMachine.state is Finish) {
            onFinish(omokStateMachine.state)
            return
        }
    }

    fun restoreGame(stones: List<OmokStone>) {
        board.setStones(stones)
        omokStateMachine.state = if (stones.size % 2 == 0) BlackStoneTurn else WhiteStoneTurn
    }

    private fun process(
        position: Position,
        onPlace: (StoneType) -> Unit,
    ) {
        val omokStone = OmokStone(position, omokStateMachine.state.stoneType)
        require(rule.checkAnyFoulCondition(omokStone, board)) { RENJURULE_MESSAGE }
        board.placeStone(omokStone)
        onPlace(omokStone.stoneType)

        val event =
            when {
                rule.checkWin(omokStone, board) -> OmokEvent.WIN
                board.isFull() -> OmokEvent.DRAW
                else -> OmokEvent.TURN
            }

        omokStateMachine.transition(event)
    }

    companion object {
        private const val RENJURULE_MESSAGE = "해당 위치에는 돌을 놓을 수 없습니다."
    }
}
