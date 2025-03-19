package omok.model.domain.rule

import omok.model.domain.omokboard.OmokBoard
import omok.model.domain.player.PlayerStone

class InvalidPositionRule : OmokRule {
    override fun canPlace(
        omokBoard: OmokBoard,
        playerStone: PlayerStone,
    ): PlaceResult =
        if (omokBoard.find(playerStone.position) == null) {
            PlaceResult.Failure.InvalidPosition
        } else {
            PlaceResult.Success.Progress(playerStone)
        }
}
