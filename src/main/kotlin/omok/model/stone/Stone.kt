package omok.model.stone

import omok.model.board.Position

interface Stone {
    fun position(): Position

    fun color(): StoneColor
}
