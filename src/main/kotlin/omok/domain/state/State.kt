package omok.domain.state

import omok.domain.stone.Stones

interface State {
    val blackStones: Stones
    val whiteStones: Stones
}
