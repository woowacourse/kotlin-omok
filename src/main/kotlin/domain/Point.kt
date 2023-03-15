package domain

open class Point(open val x: Int, open val y: Int) {
    open operator fun plus(other: Point): Point {
        return Point(x + other.x, y + other.y)
    }

    open operator fun times(other: Int): Point {
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
