package model.domain

class Board(system: Map<Coordination, MutableList<Coordination>>) {
    companion object {
        private const val SIZE = 15
        private const val CORRECTION_VALUE = 1

        private val OMOK_BOARD: Map<Coordination, MutableList<Coordination>> =
            List(SIZE) { row ->
                Coordination.fromNotNull(row + CORRECTION_VALUE) to mutableListOf<Coordination>()
            }.toMap()

        fun create(): Board = Board(OMOK_BOARD)
    }
}
