package omok.domain.rule

import omok.domain.omokboard.OmokBoard
import omok.domain.placeresult.GameFinish
import omok.domain.placeresult.GameProgress
import omok.domain.placeresult.PlaceResult
import omok.domain.player.PlayerStone

class DrawRule : OmokRule {
    override fun place(
        omokBoard: OmokBoard,
        playerStone: PlayerStone,
    ): PlaceResult =
        if (omokBoard.isOneEmptyLeft) {
            GameFinish(GameResult.DRAW)
        } else {
            GameProgress()
        }
}
