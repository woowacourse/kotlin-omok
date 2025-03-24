package omok.domain

import omok.domain.model.Board
import omok.domain.model.position.OmokStone
import omok.domain.model.position.Position
import omok.domain.model.state.Finish
import omok.domain.model.state.OmokState
import omok.domain.model.state.Running
import omok.domain.model.stone.StoneType

class Game(private val state: OmokState) {
    fun play(
        onBeforePlace: (Board, StoneType, OmokStone?) -> Unit,
        onPlace: () -> Position,
    ): StoneType {
        return play(state, onBeforePlace, onPlace).stoneType
    }

    private tailrec fun play(
        state: OmokState,
        onBeforePlace: (Board, StoneType, OmokStone?) -> Unit,
        onPlace: () -> Position,
    ): OmokState {
        onBeforePlace(state.board, state.stoneType, state.board.lastStoneOrNull())
        return when (val newState = state.placeStone(onPlace)) {
            is Running -> play(newState, onBeforePlace, onPlace)
            is Finish -> newState
        }
    }
}
