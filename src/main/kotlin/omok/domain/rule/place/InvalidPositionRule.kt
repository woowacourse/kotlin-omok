package omok.domain.rule.place

import omok.domain.omokboard.OmokBoard
import omok.domain.player.PlayerStone

class InvalidPositionRule : PlaceRule {
    override fun perform(
        omokBoard: OmokBoard,
        playerStone: PlayerStone,
    ): PlaceResult =
        if (omokBoard.find(playerStone.position) == null) {
            PlaceResult.Failure.InvalidPosition
        } else {
            PlaceResult.Success(playerStone)
        }
}
