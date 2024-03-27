package omok.model.game

import omok.model.Position

data class GamePlayersEvent(
    val startingPlayerPlaceEvent: () -> Position,
    val opponentPlaceEvent: () -> Position,
)
