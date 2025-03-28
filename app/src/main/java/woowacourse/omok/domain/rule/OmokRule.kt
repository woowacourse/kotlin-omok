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
        private val board = OmokBoard()

        val whiteRules: List<OmokRule> get() =
            listOf(
                InvalidPositionRule(),
                DrawRule(),
                WinningRule(),
            )

        val blackRules: List<OmokRule> get() =
            listOf(
                InvalidPositionRule(),
                ExternalRenjuRule(BlackRenjuRule(board.width, board.height)),
                DrawRule(),
                WinningRule(),
            )
    }
}
