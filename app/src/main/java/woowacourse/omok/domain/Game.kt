package omok.domain

import omok.domain.model.Board
import omok.domain.model.position.Position
import omok.domain.model.rule.OmokRule
import omok.domain.model.state.Finish
import omok.domain.model.state.OmokEvent
import omok.domain.model.state.OmokStateMachine
import omok.domain.model.stone.OmokStone
import omok.domain.model.stone.StoneType

class Game(
    val board: Board = Board(),
    private val rule: OmokRule,
    private val omokStateMachine: OmokStateMachine = OmokStateMachine(),
) {
    fun play(
        onBeforePlace: (Board, StoneType, OmokStone?) -> Unit,
        onPlace: () -> Position,
        onFailure: (String) -> Unit,
    ) {
        while (omokStateMachine.state !is Finish) {
            runCatching {
                onBeforePlace(board, omokStateMachine.state.stoneType, board.getLastStone())
                process(onPlace())
            }.onFailure { onFailure(it.message ?: it.stackTraceToString()) }
        }
    }

    private fun process(position: Position) {
        val omokStone = OmokStone(position, omokStateMachine.state.stoneType)
        require(rule.checkAnyFoulCondition(omokStone, board)) { RENJURULE_MESSAGE }
        board.placeStone(omokStone)

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
