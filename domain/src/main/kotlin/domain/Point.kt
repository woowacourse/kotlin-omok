package domain

data class Point( val x: Int,  val y: Int) {
     operator fun plus(other: Point): Point {
        return Point(x + other.x, y + other.y)
    }

    operator fun times(other: Int): Point {
        return Point(x * other, y * other)
    }

    override fun equals(other: Any?): Boolean {
        if (other is Point) {
            return other.x == this.x && other.y == this.y
        }
        return false
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }
}
