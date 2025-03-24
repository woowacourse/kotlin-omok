package omok.domain.rule

import omok.domain.omokboard.OmokBoard
import omok.domain.omokboard.PointState
import omok.domain.placeresult.GameOnGoing
import omok.domain.placeresult.InvalidMove
import omok.domain.placeresult.PlaceResult
import omok.domain.player.PlayerStone

class InvalidPositionRule : OmokRule {
    override fun place(
        omokBoard: OmokBoard,
        playerStone: PlayerStone,
    ): PlaceResult =

        when (omokBoard.find(playerStone.position)) {
            null -> InvalidMove.InvalidPosition
            is PointState.Empty -> GameOnGoing
            is PointState.OCCUPIED -> InvalidMove.AlreadyExistStone
        }
}
