package omok.domain.rule

import omok.domain.omokboard.OmokBoard
import omok.domain.player.PlayerStone

interface OmokRule {
    fun perform(
        omokBoard: OmokBoard,
        playerStone: PlayerStone,
    ): OmokResult
}
