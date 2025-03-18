package omok

class Board {
    val board: List<MutableList<IntersectionState>> = List(15) { MutableList(15) { IntersectionState.EMPTY } }

    fun place(intersection: Intersection) {
        board[intersection.position.row.value][intersection.position.column.value] = intersection.state
    }

    fun check(intersection: Intersection): BoardState {
        val verticalCheck: Boolean = checkLine(intersection, 0)
        val horizontalCheck = checkLine(intersection, 2)
        val diagonalDown = checkLine(intersection, 4)
        val diagonalUp = checkLine(intersection, 6)

        if (verticalCheck || horizontalCheck || diagonalDown || diagonalUp) {
            if (intersection.state == IntersectionState.BLACK)
                return BoardState.BLACK_OMOK
            if (intersection.state == IntersectionState.WHITE)
                return BoardState.WHITE_OMOK
        }
        return BoardState.PLAYING
    }

    private fun checkLine(intersection: Intersection, startDir: Int): Boolean {
        return 1 + checkDirection(intersection, startDir) + checkDirection(intersection, startDir + 1) >= 5
    }

    private fun checkDirection(intersection: Intersection, dir: Int): Int {
        var curX = intersection.position.row.value
        var curY = intersection.position.column.value
        val curColor = intersection.state

        val direction = listOf(-1 to 0, 1 to 0, 0 to -1, 0 to 1, -1 to 1, 1 to -1, -1 to -1, 1 to 1)

        var count = 0
        while (true) {
            if (count >= 5)
                return 5

            val nextX = curX + direction[dir].first
            val nextY = curY + direction[dir].second

            if (nextX <= 0 || nextX >= 16 || nextY <= 0 || nextY >= 16)
                break

            if (board[nextX][nextY] == curColor)
                count++
            else
                break

            curX = nextX
            curY = nextY
        }
        return count
    }
}
