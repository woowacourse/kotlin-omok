package omok.model.domain.player

import omok.model.domain.omokboard.Position

data class PlayerStone(
    val color: StoneColor,
    val position: Position,
)
