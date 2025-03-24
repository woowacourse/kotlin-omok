package omok.domain.state

import omok.domain.stone.StoneColor

sealed class Finished : State {
    class Win(val winnerColor: StoneColor) : Finished()

    data object Draw : Finished()
}
