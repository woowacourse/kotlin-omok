package omok.domain.rule

import omok.domain.omokboard.OmokBoard
import omok.domain.omokboard.State
import omok.domain.placeresult.Failure
import omok.domain.placeresult.PlaceResult
import omok.domain.placeresult.Success
import omok.domain.player.PlayerStone

class AlreadyExistStoneRule : OmokRule {
    override fun place(
        omokBoard: OmokBoard,
        playerStone: PlayerStone,
    ): PlaceResult =
        if (omokBoard.find(playerStone.position)?.state == State.EMPTY) {
            Success.Progress(playerStone)
        } else {
            Failure.AlreadyExistStone
        }
}
