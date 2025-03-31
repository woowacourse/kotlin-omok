package woowacourse.omok.domain.rule

import rule.BlackRenjuRule
import woowacourse.omok.domain.omokboard.OmokBoard
import woowacourse.omok.domain.placeresult.GameOnGoing
import woowacourse.omok.domain.placeresult.PlaceResult
import woowacourse.omok.domain.player.PlayerStone

class OmokRules(private val rules: List<OmokRule>) : OmokRule {
    override fun place(
        omokBoard: OmokBoard,
        playerStone: PlayerStone,
    ): PlaceResult {
        for (rule in rules) {
            val result = rule.place(omokBoard, playerStone)
            if (result !is GameOnGoing) {
                return result
            }
        }
        return GameOnGoing
    }

    companion object {
        private val board = OmokBoard()

        val whiteRules =
            OmokRules(
                listOf(
                    InvalidPositionRule(),
                    DrawRule(),
                    WinningRule(),
                ),
            )

        val blackRules =
            OmokRules(
                listOf(
                    InvalidPositionRule(),
                    ExternalRenjuRule(BlackRenjuRule(board.width, board.height)),
                    DrawRule(),
                    WinningRule(),
                ),
            )
    }
}
