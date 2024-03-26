package omok.library

class MoreThanFiveRule(boardSize: Int) : OmokRule(boardSize) {
    override fun validate(
        board: List<List<Int>>,
        position: Pair<Int, Int>,
    ): Boolean = directions.map { direction -> checkBlackWin(board, position, direction) }.contains(true)

    private fun checkBlackWin(
        board: List<List<Int>>,
        position: Pair<Int, Int>,
        direction: Pair<Int, Int>,
    ): Boolean {
        val oppositeDirection = direction.let { (dx, dy) -> Pair(-dx, -dy) }
        val (stone1, blink1) = search(board, position, oppositeDirection)
        val (stone2, blink2) = search(board, position, direction)
        return blink1 + blink2 == NO_BLINK && stone1 + stone2 > MORE_THAN_FIVE
    }

    companion object {
        private const val NO_BLINK = 0
        private const val MORE_THAN_FIVE = 4
    }
}
