package woowacourse.omok.domain.event

import woowacourse.omok.domain.Game
import woowacourse.omok.domain.model.stone.Stones

interface GameEvent {
    fun initBoard(omokGame: Game)

    fun showInitStones(stones: Stones)
}
