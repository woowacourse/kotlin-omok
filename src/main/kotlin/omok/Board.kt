package omok

class Board {
    val board: List<MutableList<IntersectionState>> = List(15) { MutableList(15) { IntersectionState.EMPTY } }

    fun place(intersection: Intersection) {
        board[intersection.position.row.value][intersection.position.column.value] = intersection.state
    }
}
