package omok.library

class BlackWinRule(boardSize: Int) : OmokRule(boardSize) {
    override fun validate(board: List<List<Int>>, position: Pair<Int, Int>): Boolean =
        directions.map { direction -> checkWin(board, position, direction) }.contains(true)

    private fun checkWin(
        board: List<List<Int>>,
        position: Pair<Int, Int>,
        direction: Pair<Int, Int>
    ): Boolean {
        val oppositeDirection = direction.let { (dx, dy) -> Pair(-dx, -dy) }
        val (stone1, blink1) = search(board, position, oppositeDirection)
        val (stone2, blink2) = search(board, position, direction)
        return when {
            blink1 + blink2 == CONTINUOUS_STONE && stone1 + stone2 == WIN_CONDITION -> true
            else -> false
        }
    }
    companion object {
        const val CONTINUOUS_STONE = 0
        const val WIN_CONDITION = 4
    }
}
