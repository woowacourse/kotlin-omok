package woowacourse.omok.domain.model.rule.judge

import woowacourse.omok.domain.model.player.StoneColor

sealed class JudgeResult {
    sealed class Finished : JudgeResult() {
        data class Win(
            val stone: StoneColor,
        ) : Finished()

        data object Draw : Finished()
    }

    data object NotFinished : JudgeResult()
}
