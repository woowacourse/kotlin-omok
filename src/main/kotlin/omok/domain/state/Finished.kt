package omok.domain.state

import omok.domain.stone.StoneColor

interface Finished: State {
    val winnerColor: StoneColor?
}
