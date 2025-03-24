package omok.domain.rule

import omok.domain.omokboard.OmokBoard
import omok.domain.placeresult.GameNotProgress
import omok.domain.placeresult.GameProgress
import omok.domain.placeresult.PlaceResult
import omok.domain.player.PlayerStone

class InvalidPositionRule : OmokRule {
    override fun place(
        omokBoard: OmokBoard,
        playerStone: PlayerStone,
    ): PlaceResult =
        if (omokBoard.find(playerStone.position) == null) {
            GameNotProgress.InvalidPosition
        } else {
            GameProgress()
        }
}
