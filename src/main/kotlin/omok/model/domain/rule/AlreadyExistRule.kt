package omok.model.domain.rule

import omok.model.domain.omokboard.OmokBoard
import omok.model.domain.omokboard.PointState
import omok.model.domain.player.PlayerStone

class AlreadyExistRule : OmokRule {
    override fun canPlace(
        omokBoard: OmokBoard,
        playerStone: PlayerStone,
    ): PlaceResult =
        if (omokBoard.find(playerStone.position)?.state == PointState.EMPTY) {
            PlaceResult.Success.Progress(playerStone)
        } else {
            PlaceResult.Failure.AlreadyExist
        }
}
