package omok.domain.rule.place

import omok.domain.omokboard.IntersectionState
import omok.domain.omokboard.OmokBoard
import omok.domain.player.PlayerStone

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
