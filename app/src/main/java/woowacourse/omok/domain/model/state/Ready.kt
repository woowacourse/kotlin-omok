package woowacourse.omok.domain.model.state

import woowacourse.omok.domain.model.Point
import woowacourse.omok.domain.model.stone.BlackStones
import woowacourse.omok.domain.model.stone.StoneColor
import woowacourse.omok.domain.model.stone.WhiteStones

class Ready(
    override val blackStones: BlackStones = BlackStones(),
    override val whiteStones: WhiteStones = WhiteStones(),
) : Playing() {
    override fun place(
        point: Point,
        boardSize: Int,
        onBoardUpdated: (BlackStones, WhiteStones) -> Unit,
    ): WhiteTurn {
        val newStones = blackStones + point
        onBoardUpdated(newStones, whiteStones)
        return WhiteTurn(newStones, whiteStones)
    }

    override fun nextStoneColor(): StoneColor = StoneColor.BLACK
}
