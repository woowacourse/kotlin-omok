package model.domain

class Board(val system: Map<Int, MutableList<Stone>>) {
    companion object {
        private const val SIZE = 15
        private const val CORRECTION_VALUE = 1

        private val OMOK_BOARD: Map<Int, MutableList<Stone>> =
            List(SIZE) { row ->
                row to MutableList(SIZE) { Stone.EMPTY }
            }.toMap()

        fun create(): Board = Board(OMOK_BOARD)
    }

    fun get(location: Location): Stone? = system[location.coordinationX.value]?.get(location.coordinationY.value)

    fun placeStone(location: Location, stone: Stone): Boolean {
        val row = location.coordinationX.value
        val col = location.coordinationY.value
        if (row !in 0 until SIZE || col !in 0 until SIZE) return false
        system[row]?.set(col, stone)?.let { return true } ?: return false
    }


}
