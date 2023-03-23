package domain

class XCoordinate private constructor(val x: Char) {

    override fun equals(other: Any?): Boolean = if (other is XCoordinate) x == other.x else false

    override fun hashCode(): Int = x.hashCode()

    override fun toString(): String = x.toString()

    companion object {

        private val COORDINATES: Map<Char, XCoordinate> = ('A' until 'A' + Board.SIZE).associateWith { XCoordinate(it) }

        fun of(x: Char): XCoordinate {
            val upperX = x.uppercase()[0]
            return COORDINATES[upperX] ?: XCoordinate(upperX)
        }
        fun of(x: Int): XCoordinate {
            val charX = 'A' + x - 1
            return COORDINATES[charX] ?: XCoordinate(charX)
        }
    }
}
