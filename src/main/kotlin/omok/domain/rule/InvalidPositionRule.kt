package omok.domain.rule

import omok.domain.omokboard.OmokBoard
import omok.domain.placeresult.Failure
import omok.domain.placeresult.PlaceResult
import omok.domain.placeresult.Success
import omok.domain.player.PlayerStone

class InvalidPositionRule : OmokRule {
    override fun place(
        omokBoard: OmokBoard,
        playerStone: PlayerStone,
    ): PlaceResult =
        if (omokBoard.find(playerStone.position) == null) {
            Failure.InvalidPosition
        } else {
            Success.Progress(playerStone)
        }
}
