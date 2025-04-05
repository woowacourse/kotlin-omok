package woowacourse.omok.domain.model.rule.judge

import woowacourse.omok.domain.model.omokboard.OmokBoard
import woowacourse.omok.domain.model.omokboard.PointState
import woowacourse.omok.domain.model.player.PlayerStone

class DrawRule : JudgeRule {
    override fun perform(
        board: OmokBoard,
        stone: PlayerStone,
    ): JudgeResult =
        if (board.snapshot.values.count { it == PointState.EMPTY } == 1) {
            JudgeResult.Finished.Draw
        } else {
            JudgeResult.NotFinished
        }
}
