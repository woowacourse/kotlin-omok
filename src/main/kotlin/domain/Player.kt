package domain

interface Player {
    fun place(stones: Stones, coordinateReader: CoordinateReader)
}
