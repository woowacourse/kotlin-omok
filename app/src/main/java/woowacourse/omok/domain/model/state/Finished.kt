package woowacourse.omok.domain.model.state

import woowacourse.omok.domain.model.stone.StoneColor

sealed class Finished : State {
    class Win(val winnerColor: StoneColor) : Finished()

    data object Draw : Finished()
}
