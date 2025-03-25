package omok.domain.model.rule

import omok.domain.model.position.Stone
import omok.domain.model.stone.Stones

interface RenjuRule {
    fun checkWin(
        stones: Stones,
        stone: Stone,
    ): Boolean

    fun canPlace(
        stones: Stones,
        stone: Stone,
    ): PlaceResult
}
