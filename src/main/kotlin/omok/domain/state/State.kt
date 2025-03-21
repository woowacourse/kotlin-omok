package omok.domain.state

import omok.domain.stone.BlackStones
import omok.domain.stone.WhiteStones

interface State {
    val blackStones: BlackStones
    val whiteStones: WhiteStones
}
