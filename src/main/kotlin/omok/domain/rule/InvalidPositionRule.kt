package omok.domain.rule

import omok.domain.omokboard.OmokBoard
import omok.domain.omokboard.OmokBoardPointState
import omok.domain.placeresult.GameOnGoing
import omok.domain.placeresult.InvalidMove
import omok.domain.placeresult.PlaceResult
import omok.domain.player.PlayerStone

class InvalidPositionRule : OmokInvalidMoveRule {
    override fun place(
        omokBoard: OmokBoard,
        playerStone: PlayerStone,
    ): PlaceResult =

        when (omokBoard.find(playerStone.position)) {
            null -> InvalidMove.InvalidPosition
            is OmokBoardPointState.Empty -> GameOnGoing
            is OmokBoardPointState.OCCUPIED -> InvalidMove.AlreadyExistStone
        }
}
