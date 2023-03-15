package domain

class WhitePlayer(private val color: Color = Color.WHITE) : Player {
    override fun place(stones: Stones, coordinateReader: CoordinateReader): Stone {
        val stone = Stone(color, coordinateReader.read(color))
        stones.place(stone)
        return stone
    }
}
