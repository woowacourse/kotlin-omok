package woowacourse.omok.domain.model.state

import woowacourse.omok.domain.model.stone.StoneType

class Turn(
    override val stoneType: StoneType,
) : OmokState {
    override fun play(): Turn = Turn(stoneType.reverse())

    override fun finish(): Finish = Finish(stoneType)

    override fun isFinished(): Boolean = false
}
