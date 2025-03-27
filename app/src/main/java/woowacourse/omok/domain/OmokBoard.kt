package woowacourse.omok.domain

import woowacourse.omok.domain.rule.OmokRule
import woowacourse.omok.domain.rule.Violation
import woowacourse.omok.domain.stone.OmokStones
import woowacourse.omok.domain.stone.Stone

class OmokBoard(
    private val size: Int = DEFAULT_BOARD_SIZE,
    val rule: OmokRule = OmokRule(size),
    val stones: OmokStones = OmokStones(),
) {
    init {
        require(size >= MINIMUM_BOARD_SIZE) { ERROR_INVALID_BOARD_SIZE }
    }

    fun checkViolation(stone: Stone): Violation {
        if (!stone.point.isInBounds(size)) return Violation.OUT_OF_BOARD
        if (stones.contains(stone.point)) return Violation.OCCUPIED
        return rule.checkViolation(stones, stone)
    }

    fun place(stone: Stone): OmokBoard = OmokBoard(size, rule, stones + stone)

    fun isOmok(stone: Stone): Boolean = rule.isOmok(stones, stone)

    fun isFull(): Boolean = stones.stones.size >= size * size

    companion object {
        const val DEFAULT_BOARD_SIZE = 15
        private const val MINIMUM_BOARD_SIZE = 5
        private const val ERROR_INVALID_BOARD_SIZE = "오목판의 사이즈는 최소 5x5이어야 합니다."
    }
}
