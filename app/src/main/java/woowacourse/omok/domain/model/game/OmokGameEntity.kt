package woowacourse.omok.domain.model.game

import woowacourse.omok.domain.model.omokboard.OmokBoard
import woowacourse.omok.domain.model.player.StoneColor

data class OmokGameEntity(
    val lastTurn: StoneColor,
    val board: OmokBoard,
)
