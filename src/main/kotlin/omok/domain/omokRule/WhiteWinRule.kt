package omok.domain.omokRule

object WhiteWinRule : OmokRule(WHITE_STONE, BLACK_STONE) {
    override fun validate(board: List<List<Int>>, position: Pair<Int, Int>): Boolean =
        directions.map { direction -> checkBlackWin(board, position, direction) }.contains(true)

    private fun checkBlackWin(board: List<List<Int>>, position: Pair<Int, Int>, direction: Pair<Int, Int>): Boolean {
        val direction2 = direction.let { (dx, dy) -> -dx to -dy }
        val (stone1, blink1) = search(board, position, direction2)
        val (stone2, blink2) = search(board, position, direction)

        return when {
            blink1 + blink2 == 0 && stone1 + stone2 >= 4 -> true
            else -> false
        }
    }
}
