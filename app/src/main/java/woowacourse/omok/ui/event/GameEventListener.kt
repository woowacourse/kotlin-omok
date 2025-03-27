package woowacourse.omok.ui.event

import woowacourse.omok.domain.exception.Exceptions
import woowacourse.omok.domain.point.Point
import woowacourse.omok.domain.service.OmokGame
import woowacourse.omok.domain.stone.StoneColor

interface GameEventListener {
    fun onClickPoint(
        point: Point,
        game: OmokGame,
    )

    fun onPlacedStone(stoneColor: StoneColor)

    fun onFinishedGame(color: StoneColor)

    fun onFailToAddStone(e: Exceptions)
}
