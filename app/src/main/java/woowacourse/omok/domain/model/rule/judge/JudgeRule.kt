package woowacourse.omok.domain.model.rule.judge

import woowacourse.omok.domain.model.omokboard.OmokBoard
import woowacourse.omok.domain.model.player.PlayerStone

interface JudgeRule {
    fun perform(
        board: OmokBoard,
        stone: PlayerStone,
    ): JudgeResult
}
