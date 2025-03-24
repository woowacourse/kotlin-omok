package omok.domain.rule

import omok.domain.omokboard.OmokBoard
import omok.domain.placeresult.PlaceResult
import omok.domain.player.PlayerStone
import rule.BlackRenjuRule

interface OmokRule {
    fun place(
        omokBoard: OmokBoard,
        playerStone: PlayerStone,
    ): PlaceResult

    companion object {
        val rules: List<OmokRule> get() {
            val board = OmokBoard.create()
            return listOf(
                InvalidPositionRule(),
                ExternalRenjuRule(BlackRenjuRule(board.width, board.height)),
                DrawRule(),
                WinningRule(),
            )
        }
    }
}
