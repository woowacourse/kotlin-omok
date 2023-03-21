package domain

fun interface CoordinateReader {
    fun read(color: Color): Coordinate
}
