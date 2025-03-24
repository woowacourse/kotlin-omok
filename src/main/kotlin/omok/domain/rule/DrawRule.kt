package omok.domain.rule

import omok.domain.omokboard.OmokBoard
import omok.domain.placeresult.PlaceResult
import omok.domain.placeresult.Success
import omok.domain.player.PlayerStone

class DrawRule : OmokRule {
    override fun place(
        omokBoard: OmokBoard,
        playerStone: PlayerStone,
    ): PlaceResult =
        if (omokBoard.isOneEmptyLeft) {
            Success.Finish(GameResult.DRAW)
        } else {
            Success.Progress(playerStone)
        }
}
