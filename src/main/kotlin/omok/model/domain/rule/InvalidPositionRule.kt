package omok.model.domain.rule

import omok.model.domain.omokboard.OmokBoard
import omok.model.domain.player.PlayerStone

class InvalidPositionRule : OmokRule {
    override fun canPlace(
        omokBoard: OmokBoard,
        playerStone: PlayerStone,
    ): PlaceResult =
        if (omokBoard.value.find { it.position == playerStone.position } != null) {
            PlaceResult.Success.Progress
        } else {
            PlaceResult.Failure.InvalidPosition
        }
}
