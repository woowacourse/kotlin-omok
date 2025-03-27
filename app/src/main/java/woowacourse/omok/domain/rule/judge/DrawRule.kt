package woowacourse.omok.domain.rule.judge

import woowacourse.omok.domain.omokboard.IntersectionState
import woowacourse.omok.domain.omokboard.OmokBoard
import woowacourse.omok.domain.player.PlayerStone
import woowacourse.omok.domain.rule.OmokResult

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
