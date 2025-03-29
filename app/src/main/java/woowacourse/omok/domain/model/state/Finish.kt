package woowacourse.omok.domain.model.state

import woowacourse.omok.domain.model.stone.StoneType

class Finish(
    override val stoneType: StoneType,
) : OmokState {
    override fun turn(): Turn = Turn(StoneType.BLACK)

    override fun finish(): Finish = this

    override fun isFinished(): Boolean = true
}
