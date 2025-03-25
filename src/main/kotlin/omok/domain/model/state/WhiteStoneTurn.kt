package omok.domain.model.state

import omok.domain.model.stone.StoneType

data object WhiteStoneTurn : OmokState {
    override val stoneType: StoneType = StoneType.WHITE

    override fun updateState(): OmokState = BlackStoneTurn
}
