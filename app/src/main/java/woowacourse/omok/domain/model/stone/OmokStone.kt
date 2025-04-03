package woowacourse.omok.domain.model.stone

import woowacourse.omok.domain.model.position.Position

data class OmokStone(
    val position: Position,
    val stoneType: StoneType,
)
