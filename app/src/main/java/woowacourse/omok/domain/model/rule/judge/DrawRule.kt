package woowacourse.omok.domain.model.rule.judge

import woowacourse.omok.domain.model.omokboard.IntersectionState
import woowacourse.omok.domain.model.omokboard.OmokBoard
import woowacourse.omok.domain.model.player.PlayerStone
import woowacourse.omok.domain.model.rule.OmokResult

class DrawRule : JudgeRule {
    override fun perform(
        omokBoard: OmokBoard,
        playerStone: PlayerStone,
    ): OmokResult =
        if (omokBoard.snapshot.values.count { it == IntersectionState.EMPTY } == 1) {
            JudgeResult.Finished.Draw
        } else {
            JudgeResult.NotFinished
        }
}
