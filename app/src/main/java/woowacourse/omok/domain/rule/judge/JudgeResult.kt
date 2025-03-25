package woowacourse.omok.domain.rule.judge

import omok.domain.player.StoneColor
import omok.domain.rule.OmokResult

sealed class JudgeResult : OmokResult {
    sealed class Finished : JudgeResult() {
        data class Win(
            val stone: StoneColor,
        ) : Finished()

        data object Draw : Finished()
    }

    data object NotFinished : JudgeResult()
}
