package omok.domain.model.state

import omok.domain.model.stone.StoneType

data object BlackStoneTurn : OmokState {
    override val stoneType: StoneType = StoneType.BLACK

    override fun updateState(): OmokState = WhiteStoneTurn
}
