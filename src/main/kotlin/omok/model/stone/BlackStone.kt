package omok.model.stone

import omok.model.board.Position

data class BlackStone(
    override val position: Position,
) : Stone {
    override val color: StoneColor = StoneColor.BLACK
}
