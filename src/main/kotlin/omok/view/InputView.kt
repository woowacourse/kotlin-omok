package omok.view

import omok.model.Position

object InputView {
    fun readPosition(): Position {
        return readln().run {
            Position.from(this)
        }
    }
}
