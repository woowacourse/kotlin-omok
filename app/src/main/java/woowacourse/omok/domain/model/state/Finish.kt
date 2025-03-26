package woowacourse.omok.domain.model.state

import woowacourse.omok.domain.model.position.Position
import woowacourse.omok.domain.model.stone.StoneType
import woowacourse.omok.domain.model.stone.Stones

class Finish(
    override val stones: Stones,
    override val stoneType: StoneType,
) : OmokState {
    override fun placeStone(
        position: Position,
        onPlaceMessage: (String) -> Unit,
    ): OmokState {
        onPlaceMessage("끝난 상태에서 돌을 둘 수 없습니다.")
        return this
    }
}
