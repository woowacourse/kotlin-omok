package woowacourse.omok.ui.event

import woowacourse.omok.domain.exception.Exceptions
import woowacourse.omok.domain.stone.StoneColor

interface GameEventListener {
    fun onMovedStone(stoneColor: StoneColor)

    fun onFinishedGame(color: StoneColor)

    fun onFailToAddStone(e: Exceptions)
}
