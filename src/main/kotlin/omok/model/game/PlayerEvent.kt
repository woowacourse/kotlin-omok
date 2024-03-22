package omok.model.game

import omok.model.Position

data class OmokPlayersPlaceEvent(
    val startingPlayerPlaceEvent: () -> Position,
    val opponentPlaceEvent: () -> Position,
)
