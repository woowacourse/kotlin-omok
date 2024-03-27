package omok.model.game

import omok.model.OmokStone
import omok.model.Position
import omok.model.board.Board
import omok.model.game.state.GameState

private typealias PlaceOmokEvent = () -> Position

class OmokGame(
    private val state: GameState,
    private val playersEvent: GamePlayersEvent,
) {
    fun play(onStartPut: (Board, OmokStone?) -> Unit): OmokGameResult =
        play(state, onStartPut, playersEvent.startingPlayerPlaceEvent).let {
            OmokGameResult(it, it.lastStoneOrNull() ?: error("게임이 종료되지 않았습니다."))
        }

    private tailrec fun play(
        state: GameState,
        onStartPlaceStone: (Board, OmokStone?) -> Unit,
        event: PlaceOmokEvent,
    ): Board {
        onStartPlaceStone(state.board, state.board.lastStoneOrNull())
        val newState = state.placeStone(event)
        if (newState.hasOmok()) return newState.board

        return play(newState, onStartPlaceStone, changePlaceOmokEvent(event))
    }

    private fun changePlaceOmokEvent(event: PlaceOmokEvent): PlaceOmokEvent {
        if (event == playersEvent.startingPlayerPlaceEvent) return playersEvent.opponentPlaceEvent
        return playersEvent.startingPlayerPlaceEvent
    }
}
