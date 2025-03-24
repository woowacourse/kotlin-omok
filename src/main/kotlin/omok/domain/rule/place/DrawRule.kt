package omok.domain.rule.place

import omok.domain.omokboard.IntersectionState
import omok.domain.omokboard.OmokBoard
import omok.domain.player.PlayerStone
import omok.domain.rule.winning.GameResult

class DrawRule : PlaceRule {
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
