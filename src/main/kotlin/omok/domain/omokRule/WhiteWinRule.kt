package omok.domain.omokRule

object WhiteWinRule : OmokRule(WHITE_STONE, BLACK_STONE) {
    private const val CONDITION_WIN = 4
    override fun validate(board: List<List<Int>>, point: Pair<Int, Int>): Boolean {
        return directions.map { checkWin(board, point, it) }.contains(true)
    }

    private fun checkWin(board: List<List<Int>>, point: Pair<Int, Int>, direction: Pair<Int, Int>): Boolean {
        val (stone1, blink1) = search(board, point, direction)
        val (stone2, blink2) = search(board, point, direction.run { Pair(-this.first, -this.second) })

        return when {
            blink1 + blink2 == 0 && stone1 + stone2 >= CONDITION_WIN -> true
            else -> false
        }
    }
}
