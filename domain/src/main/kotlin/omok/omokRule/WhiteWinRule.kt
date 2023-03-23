package omok.omokRule

object WhiteWinRule : OmokRule(WHITE_STONE, BLACK_STONE) {
    override fun validate(board: List<List<Int>>, point: Pair<Int, Int>): Boolean {
        return directions.map { checkWin(board, point, it) }.contains(true)
    }

    private fun checkWin(board: List<List<Int>>, point: Pair<Int, Int>, direction: Pair<Int, Int>): Boolean {
        val oppositeDirection = direction.let { (dx, dy) -> Pair(-dx, -dy) }
        val (stone1, blink1) = search(board, point, oppositeDirection)
        val (stone2, blink2) = search(board, point, direction)

        return when {
            blink1 + blink2 == 0 && stone1 + stone2 >= 4 -> true
            else -> false
        }
    }
}
