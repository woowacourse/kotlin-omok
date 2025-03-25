package omok.domain.model

import omok.domain.model.stone.OmokStone
import omok.domain.model.stone.StoneType

class Board(
    val size: Int = DEFAULT_BOARD_SIZE,
    val stones: List<OmokStone> = emptyList(),
) {
    val blackStones: List<OmokStone>
        get() = getStones(StoneType.BLACK)

    val whiteStones: List<OmokStone>
        get() = getStones(StoneType.WHITE)

    fun placeStone(omokStone: OmokStone): Board {
        validate(omokStone)
        return Board(size, stones + listOf(omokStone))
    }

    fun getLastStone(): OmokStone? = stones.lastOrNull()

    fun isFull(): Boolean = stones.size == size * size

    private fun validate(omokStone: OmokStone) {
        require(stones.none { it.position == omokStone.position }) {
            DUPLICATE_MESSAGE
        }
    }

    private fun getStones(stoneType: StoneType): List<OmokStone> {
        return stones.filter { it.stoneType == stoneType }
    }

    companion object {
        const val DEFAULT_BOARD_SIZE = 15
        private const val DUPLICATE_MESSAGE = "이미 바둑돌이 존재하는 곳에는 둘 수 없습니다."
    }
}
