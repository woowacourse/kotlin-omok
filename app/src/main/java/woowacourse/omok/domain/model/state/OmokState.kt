package woowacourse.omok.domain.model.state

import woowacourse.omok.domain.model.position.Position
import woowacourse.omok.domain.model.stone.StoneType
import woowacourse.omok.domain.model.stone.Stones

interface OmokState {
    val stoneType: StoneType
    val stones: Stones

    fun placeStone(
        position: Position,
        onPlaceMessage: (String) -> Unit,
    ): OmokState
}
