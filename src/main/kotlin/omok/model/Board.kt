package omok.model

class Board {
    val board: List<List<Stone>>
        get() = _board.toList()
    private val _board: MutableList<MutableList<Stone>> =
        MutableList(15) {
            MutableList(15) {
                Stone.EMPTY
            }
        }
}
