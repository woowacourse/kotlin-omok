package model.domain.tools

class Board private constructor(
    private val _system: MutableMap<Location, Stone>,
) {
    val system: Map<Location, Stone> get() = _system.toMap()
    fun getStone(location: Location): Stone = requireNotNull(system[location])

    fun canPlaceStone(location: Location, stone: Stone): Boolean {
        if (getStone(location) == Stone.EMPTY) {
            place(location, stone)
            return true
        }
        return false
    }

    private fun place(location: Location, stone: Stone) {
        _system[location] = stone
    }

    companion object {
        private const val MINIMUM_NUMBER = 0
        private const val MAXIMUM_NUMBER = 224
        private const val SIZE = 15
        private val range = MINIMUM_NUMBER..MAXIMUM_NUMBER
        fun create(): Board {
            val locations = (range).map { number ->
                val x = number % SIZE
                val y = number / SIZE

                Location(x, y)
            }

            val system = locations.associateWith {
                Stone.EMPTY
            }.toMutableMap()

            return Board(system)
        }
    }
}
