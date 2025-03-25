package omok.domain.board

import omok.domain.rule.OmokRules
import omok.domain.rule.finder.Direction
import omok.domain.stone.Empty
import omok.domain.stone.OmokStones
import omok.domain.stone.Protected
import omok.domain.stone.Stone
import omok.view.BoardView

class OmokBoard(
    val omokStones: OmokStones,
    val omokRules: OmokRules,
) {
    init {
        require(MAX_ROW_SIZE <= COLUMN_POOL.size) { ERROR_OUT_OF_COLUMN_POOL }
    }

    var latestStone: Stone = Empty.dummy()
        private set

    fun isNotFull() = omokStones.stones.size != MAX_COLUMN_SIZE * MAX_ROW_SIZE

    fun view(): BoardView = BoardView(this)

    fun addStone(stone: Stone) {
        omokStones.add(stone)
        latestStone = stone
        updateProtectedPlace()
    }

    fun goto(
        currentPosition: Stone,
        direction: Direction,
    ): Stone {
        val newX = currentPosition.x + direction.x
        val newY = currentPosition.y + direction.y
        return omokStones.getPointAt(newY, newX)
    }

    private fun updateProtectedPlace() {
        omokStones.stones
            .filterIsInstance<Protected>()
            .forEach { point ->
                if (!omokRules.isProtected(point, this)) {
                    omokStones.remove(point)
                }
            }
        omokStones.getEmptyStones()
            .forEach { point ->
                if (omokRules.isProtected(point, this)) {
                    omokStones.add(Protected(point.x, point.y))
                }
            }
    }

    companion object {
        // A~Z, a~z 총 최대 52줄의 열 생성이 가능합니다
        val COLUMN_POOL = (('A'..'Z') + ('a'..'z'))
        const val MAX_COLUMN_SIZE = 15
        const val MAX_ROW_SIZE = 15
        private const val ERROR_OUT_OF_COLUMN_POOL = "문자열 풀의 사이즈보다 열의 크기가 큽니다"
    }
}
