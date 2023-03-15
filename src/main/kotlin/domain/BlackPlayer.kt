package domain

class BlackPlayer(override val color: Color = Color.BLACK) : Player {
    override fun place(stones: Stones, coordinateReader: CoordinateReader): Stone {
        val stone = Stone(color, coordinateReader.read(color))
        stones.place(stone)
        return stone
    }
}
