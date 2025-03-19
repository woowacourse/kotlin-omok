package omok.model

import rule.wrapper.point.Point

class Board {
    val board: List<MutableList<IntersectionState>> = List(16) { MutableList(16) { IntersectionState.EMPTY } }
    private var _lastStone: Intersection = Intersection(Point(1, 1), IntersectionState.EMPTY)
    val lastStone: Intersection get() = _lastStone.copy()

    val blackStones: MutableList<Point> = mutableListOf()
    val whiteStones: MutableList<Point> = mutableListOf()

    fun place(intersection: Intersection) {
        val boardState: IntersectionState = board[intersection.point.row][intersection.point.col]
        require(boardState == IntersectionState.EMPTY) { ERROR_MESSAGE_INTERSECTION_NOT_EMPTY }

        board[intersection.point.row][intersection.point.col] = intersection.state
        setLastStone(intersection)
    }

    private fun setLastStone(intersection: Intersection) {
        _lastStone = intersection
    }

    fun check(intersection: Intersection): BoardState {
        val verticalCheck: Boolean = checkLine(intersection, 0)
        val horizontalCheck = checkLine(intersection, 2)
        val diagonalDown = checkLine(intersection, 4)
        val diagonalUp = checkLine(intersection, 6)

        if (verticalCheck || horizontalCheck || diagonalDown || diagonalUp) {
            if (intersection.state == IntersectionState.BLACK) {
                return BoardState.BLACK_OMOK
            }
            if (intersection.state == IntersectionState.WHITE) {
                return BoardState.WHITE_OMOK
            }
        }
        return BoardState.PLAYING
    }

    private fun checkLine(
        intersection: Intersection,
        startDir: Int,
    ): Boolean {
        return 1 + checkDirection(intersection, startDir) + checkDirection(intersection, startDir + 1) >= 5
    }

    private fun checkDirection(
        intersection: Intersection,
        dir: Int,
    ): Int {
        var curX = intersection.point.row
        var curY = intersection.point.col
        val curColor = intersection.state

        val direction = listOf(-1 to 0, 1 to 0, 0 to -1, 0 to 1, -1 to 1, 1 to -1, -1 to -1, 1 to 1)

        var count = 0
        while (true) {
            if (count >= 5) {
                return 5
            }

            val nextX = curX + direction[dir].first
            val nextY = curY + direction[dir].second

            if (nextX <= 0 || nextX >= 16 || nextY <= 0 || nextY >= 16) {
                break
            }

            if (board[nextX][nextY] == curColor) {
                count++
            } else {
                break
            }

            curX = nextX
            curY = nextY
        }
        return count
    }

    companion object {
        private const val ERROR_MESSAGE_INTERSECTION_NOT_EMPTY = "돌은 빈 칸에만 둘 수 있습니다."
    }
}
