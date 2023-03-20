package model.domain.tools

class Board private constructor(val system: MutableMap<Location, Stone>) {
    fun get(location: Location): Stone = requireNotNull(system[location])

    fun canPlace(location: Location) = get(location) == Stone.EMPTY

    fun placeStone(location: Location, stone: Stone) {
        system[location] = stone
    }

    companion object {
        fun from(size: Int): Board {
            val system = mutableMapOf<Location, Stone>()
            val locations = mutableListOf<Location>()
            repeat(size) { x ->
                getLocation(Coordination.from(x), size).forEach { locations.add(it) }
            }
            locations.forEach { system[it] = Stone.EMPTY }
            return Board(system)
        }

        private fun getLocation(coordinationX: Coordination, size: Int) =
            List(size) {
                Location(coordinationX, Coordination.from(it))
            }
    }
}
