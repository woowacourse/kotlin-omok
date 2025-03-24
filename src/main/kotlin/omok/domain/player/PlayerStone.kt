package omok.domain.player

import omok.domain.omokboard.Position

data class PlayerStone(
    val color: StoneColor,
    val position: Position,
)
