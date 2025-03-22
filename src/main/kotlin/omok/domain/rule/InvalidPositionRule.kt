package omok.domain.rule

import omok.domain.omokboard.OmokBoard
import omok.domain.player.PlayerStone

class InvalidPositionRule : OmokRule {
    override fun place(
        omokBoard: OmokBoard,
        playerStone: PlayerStone,
    ): PlaceResult =
        if (omokBoard.find(playerStone.position) == null) {
            PlaceResult.Failure.InvalidPosition
        } else {
            PlaceResult.Success.Progress(playerStone)
        }
}
