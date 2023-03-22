package domain.stone

object StoneFactory {
    fun createSameColorStone(stone: Stone, point: Point): Stone {
        return when (stone) {
            is BlackStone -> BlackStone(point)
            else -> WhiteStone(point)
        }
    }
}