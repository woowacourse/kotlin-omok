package omok.model.stone

import omok.model.board.Position

data class BlackStone(
    private val position: Position,
) : Stone {
    private val color: StoneColor = StoneColor.BLACK

    override fun position(): Position = position

    override fun color(): StoneColor = color
}
