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
}
