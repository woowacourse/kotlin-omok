package woowacourse.omok.domain.rule

import woowacourse.omok.domain.omokboard.OmokBoard
import woowacourse.omok.domain.player.PlayerStone

interface OmokRule {
    fun perform(
        omokBoard: OmokBoard,
        playerStone: PlayerStone,
    ): OmokResult
}
