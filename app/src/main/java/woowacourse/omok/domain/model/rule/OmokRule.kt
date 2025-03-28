package woowacourse.omok.domain.model.rule

import woowacourse.omok.domain.model.omokboard.OmokBoard
import woowacourse.omok.domain.model.player.PlayerStone

interface OmokRule {
    fun perform(
        omokBoard: OmokBoard,
        playerStone: PlayerStone,
    ): OmokResult
}
