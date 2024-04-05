package omok.model.entity

data class Point(val x: Int, val y: Int) {
    operator fun plus(other: Point) = Point(x + other.x, y + other.y)

    operator fun minus(other: Point) = Point(x - other.x, y - other.y)

    operator fun times(other: Int) = Point(x * other, y * other)

    operator fun unaryMinus() = Point(-x, -y)
}
