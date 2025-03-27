package omok.domain.model.stone

import omok.domain.model.position.Position

data class OmokStone(
    val position: Position,
    val stoneType: StoneType,
)
