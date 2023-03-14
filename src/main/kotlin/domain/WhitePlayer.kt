package domain

class WhitePlayer(private val color: Color) : Player {
    override fun place(stones: Stones, coordinate: CoordinateReader): Stone {
        val stone = Stone(color, coordinate.read())
        stones.place(stone)
        return stone
    }
}
