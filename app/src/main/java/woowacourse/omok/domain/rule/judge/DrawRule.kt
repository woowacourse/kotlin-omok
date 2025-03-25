package woowacourse.omok.domain.rule.judge

import omok.domain.omokboard.IntersectionState
import omok.domain.omokboard.OmokBoard
import omok.domain.player.PlayerStone
import omok.domain.rule.OmokResult

class DrawRule : JudgeRule {
    override fun perform(
        omokBoard: OmokBoard,
        playerStone: PlayerStone,
    ): OmokResult =
        if (omokBoard.value.values.count { it.state == IntersectionState.EMPTY } == 1) {
            JudgeResult.Finished.Draw
        } else {
            JudgeResult.NotFinished
        }
}
