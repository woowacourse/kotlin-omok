package omok.model.stone

import omok.model.board.Position

class WhiteStone(
    override val position: Position,
) : Stone {
    override val color: StoneColor = StoneColor.WHITE
}
