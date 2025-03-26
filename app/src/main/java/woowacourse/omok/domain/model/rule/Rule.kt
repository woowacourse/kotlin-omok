package woowacourse.omok.domain.model.rule

import woowacourse.omok.domain.model.position.Stone
import woowacourse.omok.domain.model.stone.Stones

interface Rule {
    fun checkWin(
        stones: Stones,
        stone: Stone,
    ): Boolean

    fun canPlace(
        stones: Stones,
        stone: Stone,
    ): PlaceResult
}
