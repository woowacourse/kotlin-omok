package woowacourse.omok.domain.model.player

import woowacourse.omok.domain.model.omokboard.Position

data class PlayerStone(
    val color: StoneColor,
    val position: Position,
)
