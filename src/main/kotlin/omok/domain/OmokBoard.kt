package omok.domain

import omok.domain.rule.BlackStoneRule
import omok.domain.rule.Violation
import omok.domain.rule.WhiteStoneRule
import omok.domain.stone.StoneColor
import omok.domain.stone.Stones

class OmokBoard(
    private val boardSize: Int,
    val blackStones: Stones = Stones(BlackStoneRule(boardSize)),
    val whiteStones: Stones = Stones(WhiteStoneRule(boardSize)),
) {
    fun put(
        stoneColor: StoneColor,
        point: Point,
    ): OmokBoard =
        when (stoneColor) {
            StoneColor.BLACK -> OmokBoard(boardSize, blackStones + point, whiteStones)
            StoneColor.WHITE -> OmokBoard(boardSize, blackStones, whiteStones + point)
        }

    fun checkViolation(
        stoneColor: StoneColor,
        point: Point,
    ): Violation {
        if (!point.isInBounds(boardSize)) return Violation.OUT_OF_BOARD
        if (contains(point)) return Violation.OCCUPIED
        return getStones(stoneColor).checkViolation(getOtherStones(stoneColor), point)
    }

    fun isOmok(
        stoneColor: StoneColor,
        point: Point,
    ): Boolean = getStones(stoneColor).isOmok(point)

    fun isFull(): Boolean = blackStones.points.size + whiteStones.points.size >= boardSize * boardSize

    private fun contains(point: Point): Boolean = blackStones.contains(point) || whiteStones.contains(point)

    private fun getStones(color: StoneColor): Stones =
        when (color) {
            StoneColor.BLACK -> blackStones
            StoneColor.WHITE -> whiteStones
        }

    private fun getOtherStones(color: StoneColor): Stones =
        when (color) {
            StoneColor.BLACK -> whiteStones
            StoneColor.WHITE -> blackStones
        }
}
