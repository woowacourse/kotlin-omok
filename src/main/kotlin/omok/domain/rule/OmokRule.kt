package omok.domain.rule

import omok.domain.omokboard.OmokBoard
import omok.domain.player.PlayerStone

interface OmokRule {
    fun canPlace(
        omokBoard: OmokBoard,
        playerStone: PlayerStone,
    ): PlaceResult
}
