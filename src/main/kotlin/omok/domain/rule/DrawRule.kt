package omok.domain.rule

import omok.domain.omokboard.OmokBoard
import omok.domain.player.PlayerStone

class DrawRule : OmokRule {
    override fun place(
        omokBoard: OmokBoard,
        playerStone: PlayerStone,
    ): PlaceResult =
        if (omokBoard.isOneEmptyLeft) {
            PlaceResult.Success.Finish(GameResult.DRAW)
        } else {
            PlaceResult.Success.Progress(playerStone)
        }
}
