package omok.domain.rule

import omok.domain.omokboard.IntersectionState
import omok.domain.omokboard.OmokBoard
import omok.domain.player.PlayerStone

class AlreadyExistStoneRule : OmokRule {
    override fun canPlace(
        omokBoard: OmokBoard,
        playerStone: PlayerStone,
    ): PlaceResult =
        if (omokBoard.find(playerStone.position)?.state == IntersectionState.EMPTY) {
            PlaceResult.Success.Progress(playerStone)
        } else {
            PlaceResult.Failure.AlreadyExistStone
        }
}
