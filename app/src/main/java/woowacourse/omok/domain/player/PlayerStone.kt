package woowacourse.omok.domain.player

import woowacourse.omok.domain.omokboard.Position

data class PlayerStone(
    val color: StoneColor,
    val position: Position,
)
