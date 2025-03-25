package omok.model.rule

import omok.model.board.OmokBoard
import omok.model.board.Position

class WinRule(
    currentStone: Int,
    position: Position,
    omokBoard: OmokBoard,
) : OmokRule(currentStone, getOpponentStone(currentStone), position = position, omokBoard = omokBoard) {
    override fun validate(): Boolean = directions.map { direction -> checkWhiteWin(direction) }.contains(true)

    private fun checkWhiteWin(direction: Pair<Int, Int>): Boolean {
        val oppositeDirection = direction.let { (dx, dy) -> Pair(-dx, -dy) }
        val (stone1, blink1) = search(oppositeDirection)
        val (stone2, blink2) = search(direction)

        return when {
            blink1 + blink2 == 0 && stone1 + stone2 == 4 -> true
            else -> false
        }
    }

    companion object {
        private fun getOpponentStone(stone: Int): Int = if (stone == BLACK_STONE) WHITE_STONE else BLACK_STONE
    }
}
