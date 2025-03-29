package woowacourse.omok.domain.model.rule.place

import woowacourse.omok.domain.model.omokboard.OmokBoard
import woowacourse.omok.domain.model.omokboard.PointState
import woowacourse.omok.domain.model.player.PlayerStone

class AlreadyExistStoneRule : PlaceRule {
    override fun perform(
        board: OmokBoard,
        stone: PlayerStone,
    ): PlaceResult =
        if (board.find(stone.position) == PointState.EMPTY) {
            PlaceResult.Success
        } else {
            PlaceResult.Failure.AlreadyExistStone
        }
}
