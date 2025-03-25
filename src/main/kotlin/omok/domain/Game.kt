package omok.domain

import omok.domain.model.Board
import omok.domain.model.position.Position
import omok.domain.model.state.Finish
import omok.domain.model.state.OmokStateMachine
import omok.domain.model.stone.OmokStone
import omok.domain.model.stone.StoneType

class Game(private val omokStateMachine: OmokStateMachine) {
    fun play(
        onBeforePlace: (Board, StoneType, OmokStone?) -> Unit,
        onPlace: () -> Position,
        onFailure: (String) -> Unit,
    ): Board {
        return playRecursive(omokStateMachine, onBeforePlace, onPlace, onFailure)
    }

    private tailrec fun playRecursive(
        stateMachine: OmokStateMachine,
        onBeforePlace: (Board, StoneType, OmokStone?) -> Unit,
        onPlace: () -> Position,
        onFailure: (String) -> Unit,
    ): Board {
        onBeforePlace(stateMachine.board, stateMachine.state.stoneType, stateMachine.board.getLastStone())

        val newStateMachine =
            runCatching {
                stateMachine.placeStone(onPlace)
            }.onFailure {
                onFailure(it.message ?: it.stackTraceToString())
            }.getOrElse {
                return playRecursive(stateMachine, onBeforePlace, onPlace, onFailure)
            }

        return when (newStateMachine.state) {
            is Finish -> newStateMachine.board
            else -> playRecursive(newStateMachine, onBeforePlace, onPlace, onFailure)
        }
    }
}
