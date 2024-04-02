package woowacourse.omok.model.board

import woowacourse.omok.model.entity.Stone

class Board(private val _stones: Set<Stone> = setOf()) {
    val stones: Set<Stone>
        get() = _stones.toSet()

    fun place(stone: Stone): BoardState {
        val nextStones = _stones.plus(stone)

        return when {
            nextStones.size == MAX_SIZE * MAX_SIZE -> Full("오목판이 가득찼습니다. 더이상 둘 곳이 없습니다.")
            stones.any { it.point == stone.point } -> Duplicated("중복된 위치에 돌을 놓았습니다. 재입력 해 주세요.")
            stone.point.x !in SIZE_RANGE && stone.point.y !in SIZE_RANGE -> OutOfRange("오목판 바깥에 돌을 놓았습니다. 재입력 해 주세요.")
            else -> Success(Board(nextStones))
        }
    }

    fun previousStone(): Stone? = stones.lastOrNull()

    fun lastStoneColor(): String? = stones.lastOrNull()?.stoneColor?.name

    companion object {
        private const val MIN_SIZE = 1
        private const val MAX_SIZE = 15
        private val SIZE_RANGE = MIN_SIZE..MAX_SIZE
    }
}
