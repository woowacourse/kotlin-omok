package omok.model.domain.rule

import omok.model.domain.omokboard.OmokBoard
import omok.model.domain.player.PlayerStone

interface OmokRule {
    fun canPlace(
        omokBoard: OmokBoard,
        playerStone: PlayerStone,
    ): PlaceResult
}
