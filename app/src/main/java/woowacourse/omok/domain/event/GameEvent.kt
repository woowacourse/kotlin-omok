package woowacourse.omok.domain.event

import woowacourse.omok.domain.Play
import woowacourse.omok.domain.model.stone.Stones

interface GameEvent {
    fun initBoard(omokPlay: Play)

    fun showInitStones(stones: Stones)
}
