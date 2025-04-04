package woowacourse.omok.domain.model.state

import woowacourse.omok.domain.model.stone.BlackStones
import woowacourse.omok.domain.model.stone.StoneColor
import woowacourse.omok.domain.model.stone.WhiteStones

sealed class State {
    data class Playing(
        val blackStones: BlackStones,
        val whiteStones: WhiteStones,
        val nextStoneColor: StoneColor,
    ) : State()

    data class Finished(
        val winnerColor: StoneColor?,
    ) : State()

    sealed class Foul : State() {
        data object Duplicated : Foul()

        data object DoubleThree : Foul()

        data object DoubleFour : Foul()

        data object OverLine : Foul()
    }
}
