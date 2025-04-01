
package woowacourse.omok.domain.rule

import woowacourse.omok.domain.omokboard.OmokBoard
import woowacourse.omok.domain.placeresult.PlaceResult
import woowacourse.omok.domain.player.PlayerStone

interface OmokRule {
    fun place(
        omokBoard: OmokBoard,
        playerStone: PlayerStone,
    ): PlaceResult
}
