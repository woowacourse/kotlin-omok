package woowacourse.omok.domain.model.rule.place

import woowacourse.omok.domain.model.omokboard.IntersectionState
import woowacourse.omok.domain.model.omokboard.OmokBoard
import woowacourse.omok.domain.model.player.PlayerStone

class AlreadyExistStoneRule : PlaceRule {
    override fun perform(
        omokBoard: OmokBoard,
        playerStone: PlayerStone,
    ): PlaceResult =
        if (omokBoard.find(playerStone.position) == IntersectionState.EMPTY) {
            PlaceResult.Success
        } else {
            PlaceResult.Failure.AlreadyExistStone
        }
}
