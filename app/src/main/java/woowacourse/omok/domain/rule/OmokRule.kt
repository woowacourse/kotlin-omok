package woowacourse.omok.domain.rule

import rule.BlackRenjuRule
import woowacourse.omok.domain.omokboard.OmokBoard
import woowacourse.omok.domain.placeresult.PlaceResult
import woowacourse.omok.domain.player.PlayerStone

interface OmokRule {
    fun place(
        omokBoard: OmokBoard,
        playerStone: PlayerStone,
    ): PlaceResult

    companion object {
        private val commonRules: List<OmokRule> =
            listOf(
                InvalidPositionRule(),
                DrawRule(),
                WinningRule(),
            )

        val whiteRules: List<OmokRule> get() = commonRules

        val blackRules: List<OmokRule> get() =
            commonRules + ExternalRenjuRule(BlackRenjuRule())
    }
}
