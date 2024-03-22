package omok.model.game

import omok.model.OmokStone
import omok.model.Position
import omok.model.board.Board
import omok.model.state.GameState

private typealias PlaceOmokEvent = () -> Position

class OmokGame(
    private val state: GameState,
    private val playersEvent: OmokPlayersPlaceEvent,
) {
    fun play(onStartPut: (Board, OmokStone?) -> Unit): Board {
        return play(state, onStartPut, playersEvent.startingPlayerPlaceEvent)
    }

    tailrec fun play(
        state: GameState,
        onStartPlaceStone: (Board, OmokStone?) -> Unit,
        event: PlaceOmokEvent,
    ): Board {
        onStartPlaceStone(state.board, state.board.lastOrNull())
        val newState = state.placeStone(event)
        if (newState.isFinished) return newState.board

        return play(newState, onStartPlaceStone, changePlaceOmokEvent(event))
    }

    private fun changePlaceOmokEvent(event: PlaceOmokEvent): PlaceOmokEvent {
        if (event == playersEvent.startingPlayerPlaceEvent) return playersEvent.opponentPlaceEvent
        return playersEvent.startingPlayerPlaceEvent
    }
}
