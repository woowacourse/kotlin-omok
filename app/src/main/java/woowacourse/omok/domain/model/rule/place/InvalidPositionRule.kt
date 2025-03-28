package woowacourse.omok.domain.model.rule.place

import woowacourse.omok.domain.model.omokboard.OmokBoard
import woowacourse.omok.domain.model.player.PlayerStone

class InvalidPositionRule : PlaceRule {
    override fun perform(
        omokBoard: OmokBoard,
        playerStone: PlayerStone,
    ): PlaceResult =
        if (omokBoard.find(playerStone.position) == null) {
            PlaceResult.Failure.InvalidPosition
        } else {
            PlaceResult.Success
        }
}
