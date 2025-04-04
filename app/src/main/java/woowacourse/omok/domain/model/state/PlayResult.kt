package woowacourse.omok.domain.model.state

import woowacourse.omok.domain.model.stone.StoneColor

sealed class PlayResult {
    data class Omok(val winner: StoneColor) : PlayResult()

    data object Draw : PlayResult()

    data class Continue(val nextState: State.Playing) : PlayResult()
}
