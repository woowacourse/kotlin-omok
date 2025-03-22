package omok.domain.rule

import omok.domain.omokboard.IntersectionState
import omok.domain.omokboard.OmokBoard
import omok.domain.player.PlayerStone

class DrawRule : OmokRule {
    override fun canPlace(
        omokBoard: OmokBoard,
        playerStone: PlayerStone,
    ): PlaceResult =
        if (omokBoard.value.values.count { it.state == IntersectionState.EMPTY } == 1) {
            PlaceResult.Success.Finish(GameResult.DRAW)
        } else {
            PlaceResult.Success.Progress(playerStone)
        }
}
