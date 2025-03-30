package woowacourse.ui

import woowacourse.omok.domain.model.stone.Stones

interface GameEvent {
    fun initBoard(stones: Stones)
}
