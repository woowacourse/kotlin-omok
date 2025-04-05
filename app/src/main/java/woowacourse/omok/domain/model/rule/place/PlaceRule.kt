package woowacourse.omok.domain.model.rule.place

import woowacourse.omok.domain.model.omokboard.OmokBoard
import woowacourse.omok.domain.model.player.PlayerStone

interface PlaceRule {
    fun perform(
        board: OmokBoard,
        stone: PlayerStone,
    ): PlaceResult
}
