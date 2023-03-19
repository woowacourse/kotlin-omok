package model.domain.tools

class Board private constructor(val system: Map<Int, MutableList<Stone>>) {
    fun get(location: Location): Stone? = system[location.coordinationX.value]?.get(location.coordinationY.value)

    fun canPlace(location: Location) = get(location) == Stone.EMPTY

    fun placeStone(location: Location, stone: Stone) {
        val row = location.coordinationX.value
        val col = location.coordinationY.value
        system[row]?.set(col, stone)
    }

    companion object {
        private const val SIZE = 15
        fun create(): Board {
            val system = List(SIZE) { row ->
                row to MutableList(SIZE) { Stone.EMPTY }
            }.toMap()
            return Board(system)
        }
    }
}
