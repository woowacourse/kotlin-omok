package omok.model

data class BlackStone(
    private val position: Position,
) : Stone {
    private val color: StoneColor = StoneColor.BLACK

    override fun position(): Position = position

    override fun color(): StoneColor = color
}
