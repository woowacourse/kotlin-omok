package woowacourse.omok.domain.model.rule.place

import woowacourse.omok.domain.model.omokboard.OmokBoard
import woowacourse.omok.domain.model.player.PlayerStone

class InvalidPositionRule : PlaceRule {
    override fun perform(
        board: OmokBoard,
        stone: PlayerStone,
    ): PlaceResult =
        if (board.find(stone.position) == null) {
            PlaceResult.Failure.InvalidPosition
        } else {
            PlaceResult.Success
        }
}
