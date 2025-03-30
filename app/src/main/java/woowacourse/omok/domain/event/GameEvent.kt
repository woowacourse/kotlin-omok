package woowacourse.omok.domain.event

import woowacourse.omok.domain.model.stone.Stones

interface GameEvent {
    fun initBoard(stones: Stones)
}
