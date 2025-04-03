package woowacourse.omok.domain.model.state

import woowacourse.omok.domain.model.stone.StoneType

data object BlackStoneTurn : OmokState {
    override val stoneType: StoneType = StoneType.BLACK

    override fun updateState(): OmokState = WhiteStoneTurn
}
