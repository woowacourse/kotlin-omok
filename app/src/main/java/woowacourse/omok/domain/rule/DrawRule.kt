package woowacourse.omok.domain.rule

import woowacourse.omok.domain.omokboard.OmokBoard
import woowacourse.omok.domain.omokboard.OmokBoardGridCell
import woowacourse.omok.domain.placeresult.GameFinish
import woowacourse.omok.domain.placeresult.GameOnGoing
import woowacourse.omok.domain.placeresult.PlaceResult
import woowacourse.omok.domain.player.PlayerStone

class DrawRule : OmokGameFinishRule {
    override fun place(
        omokBoard: OmokBoard,
        playerStone: PlayerStone,
    ): PlaceResult =
        if (omokBoard.value.values.count { it is OmokBoardGridCell.Empty } == 1) {
            GameFinish(GameResult.DRAW)
        } else {
            GameOnGoing
        }
}
