package omok.domain

class WhiteStone(val position: Position) : Stone {
    override fun findPosition(value: Position): Boolean = (position == value)

    override fun getLocation() = position
}
