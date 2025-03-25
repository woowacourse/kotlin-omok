package woowacourse.omok.domain.rule.place

import woowacourse.omok.domain.omokboard.IntersectionState
import woowacourse.omok.domain.omokboard.OmokBoard
import woowacourse.omok.domain.player.PlayerStone

class AlreadyExistStoneRule : PlaceRule {
    override fun perform(
        omokBoard: OmokBoard,
        playerStone: PlayerStone,
    ): PlaceResult =
        if (omokBoard.find(playerStone.position)?.state == IntersectionState.EMPTY) {
            PlaceResult.Success(playerStone)
        } else {
            PlaceResult.Failure.AlreadyExistStone
        }
}
