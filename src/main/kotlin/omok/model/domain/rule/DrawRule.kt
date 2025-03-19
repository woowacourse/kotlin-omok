package omok.model.domain.rule

import omok.model.domain.omokboard.OmokBoard
import omok.model.domain.omokboard.PointState
import omok.model.domain.player.PlayerStone

class DrawRule : OmokRule {
    override fun canPlace(
        omokBoard: OmokBoard,
        playerStone: PlayerStone,
    ): PlaceResult =
        if (omokBoard.value.values.all { it.state != PointState.EMPTY }) {
            PlaceResult.Success.Finish(GameResult.DRAW)
        } else {
            PlaceResult.Success.Progress
        }
}
