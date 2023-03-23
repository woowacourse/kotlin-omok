package domain

class YCoordinate private constructor(val y: Int) {

    override fun equals(other: Any?): Boolean = if (other is YCoordinate) y == other.y else false

    override fun hashCode(): Int = y.hashCode()

    override fun toString(): String = y.toString()

    companion object {
        private val COORDINATES: Map<Int, YCoordinate> = (1..Board.SIZE).associateWith { YCoordinate(it) }

        fun of(y: Int): YCoordinate = COORDINATES[y] ?: YCoordinate(y)
    }
}
