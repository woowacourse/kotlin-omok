package domain

class Coordinate private constructor(val x: Int, val y: Int) {
    companion object {
        fun from(x: Int, y: Int): Coordinate? {
            if (x > 15 || y > 15 || x < 0 || y < 0)
                return null
            return Coordinate(x, y)
        }
    }
}
