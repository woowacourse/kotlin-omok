package woowacourse.omok.domain.model.state

import woowacourse.omok.domain.model.stone.StoneType

interface OmokState {
    val stoneType: StoneType

    fun play(): Turn

    fun finish(): Finish

    fun isFinished(): Boolean
}
