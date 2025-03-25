package omok.domain.model.state

import omok.domain.model.stone.StoneType

interface OmokState {
    val stoneType: StoneType

    fun updateState(): OmokState
}
