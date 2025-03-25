package woowacourse.omok.domain.rule.place

import woowacourse.omok.domain.omokboard.OmokBoard
import woowacourse.omok.domain.player.PlayerStone

class InvalidPositionRule : PlaceRule {
    override fun perform(
        omokBoard: OmokBoard,
        playerStone: PlayerStone,
    ): PlaceResult =
        if (omokBoard.find(playerStone.position) == null) {
            PlaceResult.Failure.InvalidPosition
        } else {
            PlaceResult.Success(playerStone)
        }
}
