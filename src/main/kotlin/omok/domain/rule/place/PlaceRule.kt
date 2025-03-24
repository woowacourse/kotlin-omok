package omok.domain.rule.place

import omok.domain.omokboard.OmokBoard
import omok.domain.player.PlayerStone

interface PlaceRule {
    fun canPlace(
        omokBoard: OmokBoard,
        playerStone: PlayerStone,
    ): PlaceResult
}
