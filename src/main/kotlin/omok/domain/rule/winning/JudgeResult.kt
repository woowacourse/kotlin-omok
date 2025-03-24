package omok.domain.rule.winning

import omok.domain.player.StoneColor
import omok.domain.rule.OmokResult

sealed interface JudgeResult : OmokResult {
    sealed interface Finished : JudgeResult {
        data class Win(
            val stone: StoneColor,
        ) : Finished

        data object Draw : Finished
    }

    data object NotFinished : JudgeResult
}
