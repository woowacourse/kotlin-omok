package model.domain.state

import model.domain.Stone

class WhiteTurn : Turn() {
    override val stoneColor: Stone
        get() = Stone.WHITE
}
