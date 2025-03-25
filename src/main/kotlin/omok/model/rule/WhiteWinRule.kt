package omok.model.rule

import omok.model.board.OmokBoard
import omok.model.board.Position

class WhiteWinRule(
    position: Position,
    omokBoard: OmokBoard,
) : OmokRule(WHITE_STONE, BLACK_STONE, position, omokBoard) {
    override fun validate(): Boolean = directions.map { direction -> checkBlackWin(direction) }.contains(true)

    private fun checkBlackWin(direction: Pair<Int, Int>): Boolean {
        val oppositeDirection = direction.let { (dx, dy) -> Pair(-dx, -dy) }
        val (stone1, blink1) = search(oppositeDirection)
        val (stone2, blink2) = search(direction)

        return when {
            blink1 + blink2 == 0 && stone1 + stone2 >= 4 -> true
            else -> false
        }
    }
}
