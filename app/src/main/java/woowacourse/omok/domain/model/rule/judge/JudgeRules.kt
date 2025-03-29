package woowacourse.omok.domain.model.rule.judge

import woowacourse.omok.domain.model.omokboard.OmokBoard
import woowacourse.omok.domain.model.player.PlayerStone

@JvmInline
value class JudgeRules(
    private val value: List<JudgeRule> = emptyList(),
) : JudgeRule {
    override fun perform(
        board: OmokBoard,
        stone: PlayerStone,
    ): JudgeResult {
        value.forEach { rule ->
            val result = rule.perform(board, stone)
            if (result is JudgeResult.Finished) return result
        }
        return JudgeResult.NotFinished
    }
}
