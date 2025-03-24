package omok.domain.model.position

import omok.domain.model.stone.StoneType

data class OmokStone(
    val position: Position,
    val stoneType: StoneType,
)
