package woowacourse.omok.domain.model.state

import woowacourse.omok.domain.model.stone.StoneType

data class Finish(val winner: StoneType) : OmokState {
    override val stoneType: StoneType = winner

    override fun updateState(): OmokState = this
}
