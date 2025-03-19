package omok.model.stone

import omok.model.board.Position

class WhiteStone(
    private val position: Position,
) : Stone {
    private val color: StoneColor = StoneColor.WHITE

    override fun position(): Position = position

    override fun color(): StoneColor = color
}
