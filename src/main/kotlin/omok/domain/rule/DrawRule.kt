package omok.domain.rule

import omok.domain.omokboard.OmokBoard
import omok.domain.omokboard.OmokBoardPointState
import omok.domain.placeresult.GameFinish
import omok.domain.placeresult.GameOnGoing
import omok.domain.placeresult.PlaceResult
import omok.domain.player.PlayerStone

class DrawRule : OmokGameFinishRule {
    override fun place(
        omokBoard: OmokBoard,
        playerStone: PlayerStone,
    ): PlaceResult =
        if (omokBoard.value.values.count { it is OmokBoardPointState.Empty } == 1) {
            GameFinish(GameResult.DRAW)
        } else {
            GameOnGoing
        }
}
