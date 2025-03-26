package woowacourse.omok.domain.rule

import woowacourse.omok.domain.omokboard.OmokBoard
import woowacourse.omok.domain.omokboard.OmokBoardPointState
import woowacourse.omok.domain.placeresult.GameOnGoing
import woowacourse.omok.domain.placeresult.InvalidMove
import woowacourse.omok.domain.placeresult.PlaceResult
import woowacourse.omok.domain.player.PlayerStone

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
