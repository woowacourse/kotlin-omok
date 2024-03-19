package omok.model

open class Stone(
    val color: Color,
    open val position: Position,
)

class BlackStone(
    override val position: Position,
) : Stone(Color.BLACK, position)

class WhiteStone(
    override val position: Position,
) : Stone(Color.WHITE, position)
