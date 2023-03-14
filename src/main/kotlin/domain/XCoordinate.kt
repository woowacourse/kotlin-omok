package domain

class XCoordinate private constructor(x: Char) {

    init {
        require(x in 'A'..'O')
    }

    companion object {

        private val COORDINATES: Map<Char, XCoordinate> = ('A'..'O').associateWith { XCoordinate(it) }

        fun of(x: Char): XCoordinate {
            val upperX = x.uppercase()[0]
            return COORDINATES[upperX] ?: XCoordinate(upperX)
        }
    }
}
