package omok.model

class WhiteStone(
    private val position: Position,
) : Stone {
    private val color: StoneColor = StoneColor.WHITE

    override fun position(): Position = position

    override fun color(): StoneColor = color
}
