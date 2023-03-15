package domain

interface Player {
    val color: Color
    fun place(stones: Stones, coordinateReader: CoordinateReader): Stone
}
