package domain

fun interface CoordinateGenerator {
    fun read(color: Color): Coordinate
}
