package domain

data class Vector(val x: Int, val y: Int) {
    operator fun plus(other: Vector): Vector {
        return Vector(x + other.x, y + other.y)
    }

    operator fun times(other: Int): Vector {
        return Vector(x * other, y * other)
    }

    override fun equals(other: Any?): Boolean {
        if (other is Vector) {
            return other.x == this.x && other.y == this.y
        }
        return false
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }
}
