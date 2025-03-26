package domain.domain.state

import domain.domain.stone.StoneColor

sealed class Finished : State {
    class Win(val winnerColor: StoneColor) : Finished()

    data object Draw : Finished()
}
