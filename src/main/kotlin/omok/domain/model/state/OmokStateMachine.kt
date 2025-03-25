package omok.domain.model.state

import omok.domain.model.Board
import omok.domain.model.position.Position
import omok.domain.model.rule.OmokRule
import omok.domain.model.stone.OmokStone
import omok.domain.model.stone.StoneType

class OmokStateMachine(
    val state: OmokState = BlackStoneTurn,
    val board: Board = Board(),
    private val rule: OmokRule,
) {
    fun placeStone(onPlace: () -> Position): OmokStateMachine {
        val omokStone = OmokStone(onPlace(), state.stoneType)
        require(rule.checkAnyFoulCondition(omokStone, board)) { RENJURULE_MESSAGE }
        val newBoard = board.placeStone(omokStone)

        val newState =
            when {
                rule.checkWin(omokStone, newBoard) -> Finish(state.stoneType)
                newBoard.isFull() -> Finish(StoneType.NONE)
                else -> state.updateState()
            }

        return OmokStateMachine(newState, newBoard, rule)
    }

    companion object {
        private const val RENJURULE_MESSAGE = "해당 위치에는 돌을 놓을 수 없습니다."
    }
}
