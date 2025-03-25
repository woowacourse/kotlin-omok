package omok.domain

import omok.domain.rule.BlackStoneRule
import omok.domain.rule.WhiteStoneRule
import omok.domain.stone.StoneColor
import omok.domain.stone.Stones

class OmokBoard(
    val size: Int = DEFAULT_BOARD_SIZE,
    val blackStones: Stones = Stones(BlackStoneRule(size)),
    val whiteStones: Stones = Stones(WhiteStoneRule(size)),
) {
    init {
        require(size >= MINIMUM_BOARD_SIZE) { ERROR_INVALID_BOARD_SIZE }
    }

    fun put(
        stoneColor: StoneColor,
        point: Point,
    ): OmokBoard {
        checkViolation(stoneColor, point)
        return when (stoneColor) {
            StoneColor.BLACK -> OmokBoard(size, blackStones + point, whiteStones)
            StoneColor.WHITE -> OmokBoard(size, blackStones, whiteStones + point)
        }
    }

    fun isOmok(
        stoneColor: StoneColor,
        point: Point,
    ): Boolean = getStones(stoneColor).isOmok(point)

    fun isFull(): Boolean = blackStones.points.size + whiteStones.points.size >= size * size

    private fun checkViolation(
        stoneColor: StoneColor,
        point: Point,
    ) {
        if (!point.isInBounds(size)) throw IllegalArgumentException(ERROR_OUT_OF_BOARD)
        if (contains(point)) throw IllegalArgumentException(ERROR_ALREADY_OCCUPIED)
        if (getStones(stoneColor).isFoul(getOtherStones(stoneColor), point)) {
            throw IllegalArgumentException(ERROR_RENJU_RULE)
        }
    }

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

    companion object {
        const val DEFAULT_BOARD_SIZE = 15
        private const val MINIMUM_BOARD_SIZE = 5
        private const val ERROR_INVALID_BOARD_SIZE = "오목판의 사이즈는 최소 5x5이어야 합니다."
        private const val ERROR_OUT_OF_BOARD = "오목판의 범위를 넘어간 좌표입니다."
        private const val ERROR_ALREADY_OCCUPIED = "이미 돌이 놓여져 있습니다."
        private const val ERROR_RENJU_RULE = "돌을 놓을 수 없습니다. 금수입니다."
    }
}
