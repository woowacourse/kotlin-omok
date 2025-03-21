package omok.model.stone

import omok.model.board.Position

interface Stone {
    val position: Position

    val color: StoneColor
}
