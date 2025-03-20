package omok.domain

import omok.domain.model.Board
import omok.domain.model.position.Position
import omok.domain.model.state.Finish
import omok.domain.model.state.OmokState
import omok.domain.model.state.Running
import omok.domain.model.stone.OmokStone
import omok.domain.model.stone.StoneType

class Game(private val state: OmokState) {
    fun play(
        onBeforePlace: (Board, StoneType, OmokStone?) -> Unit,
        onPlace: () -> Position,
    ): OmokStone {
        return play(state, onBeforePlace, onPlace).let {
            it.lastStoneOrNull() ?: error("게임이 종료되지 않았습니다.")
        }
    }

    private tailrec fun play(
        state: OmokState,
        onBeforePlace: (Board, StoneType, OmokStone?) -> Unit,
        onPlace: () -> Position,
    ): Board {
        onBeforePlace(state.board, state.stoneType, state.board.lastStoneOrNull())
        return when (val newState = state.placeStone(onPlace)) {
            is Running -> play(newState, onBeforePlace, onPlace)
            is Finish -> newState.board
        }
    }
}
