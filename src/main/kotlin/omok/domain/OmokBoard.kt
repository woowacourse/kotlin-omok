package omok.domain

import omok.domain.rule.OmokRule
import omok.domain.stone.OmokStones
import omok.domain.stone.Stone

class OmokBoard(
    val size: Int = DEFAULT_BOARD_SIZE,
    val stones: OmokStones = OmokStones(OmokRule(size)),
) {
    init {
        require(size >= MINIMUM_BOARD_SIZE) { ERROR_INVALID_BOARD_SIZE }
    }

    fun place(stone: Stone): OmokBoard {
        checkViolation(stone)
        return OmokBoard(size, stones + stone)
    }

    fun isOmok(stone: Stone): Boolean = stones.isOmok(stone)

    fun isFull(): Boolean = stones.stones.size >= size * size

    private fun checkViolation(stone: Stone) {
        if (!stone.point.isInBounds(size)) throw IllegalArgumentException(ERROR_OUT_OF_BOARD)
        if (stones.contains(stone.point)) throw IllegalArgumentException(ERROR_ALREADY_OCCUPIED)
        if (stones.isFoul(stone)) {
            throw IllegalArgumentException(ERROR_RENJU_RULE)
        }
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
