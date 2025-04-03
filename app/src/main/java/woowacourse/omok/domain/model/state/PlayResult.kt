package woowacourse.omok.domain.model.state

import woowacourse.omok.domain.model.stone.StoneColor

sealed class PlayResult {
    sealed class Foul : State() {
        data object Duplicated : Foul()

        data object DoubleThree : Foul()

        data object DoubleFour : Foul()

        data object OverLine : Foul()
    }

    data class Omok(val winner: StoneColor) : PlayResult()

    data object Draw : PlayResult()

    data class Continue(val nextState: State.Playing) : PlayResult()
}
