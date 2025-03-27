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
        val rules: List<OmokRule> get() {
            val board = OmokBoard()
            return listOf(
                InvalidPositionRule(),
                ExternalRenjuRule(BlackRenjuRule(board.width, board.height)),
                DrawRule(),
                WinningRule(),
            )
        }
    }
}
