package model.domain.state

import model.domain.Stone

class BlackTurn : Turn() {
    override val stoneColor: Stone
        get() = Stone.BLACK
}
