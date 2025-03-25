package omok.domain.board

import omok.domain.rule.OmokRule
import omok.domain.rule.finder.Direction
import omok.domain.stone.Empty
import omok.domain.stone.OmokStones
import omok.domain.stone.Protected
import omok.domain.stone.Stone
import omok.view.BoardView

class OmokBoard(
    val omokStones: OmokStones,
    private val ruleChecker: OmokRule,
) {
    init {
        require(MAX_ROW_SIZE <= COLUMN_POOL.size) { ERROR_OUT_OF_COLUMN_POOL }
    }

    var latestStone: Stone = Empty.dummy()
        private set

    fun isNotFull() = omokStones.stones.size != MAX_COLUMN_SIZE * MAX_ROW_SIZE

    fun view(): BoardView = BoardView(this)

    fun pointValidation(stone: Stone) {
        require(!omokStones.isOccupied(stone)) { ERROR_OCCUPIED_POSITION }
        require(!omokStones.isProtected(stone)) { ERROR_PROTECTED_POSITION }
    }

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
                if (!ruleChecker.isProtected(point, this)) {
                    omokStones.add(Empty(point.x, point.y))
                }
            }
        omokStones.stones
            .filter { it is Empty || it is Protected }
            .forEach { point ->
                if (ruleChecker.isProtected(point, this)) {
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
        private const val ERROR_OCCUPIED_POSITION = "해당 위치에는 이미 돌이 놓여 있습니다. 다른 위치를 선택하세요."
        private const val ERROR_PROTECTED_POSITION = "해당 위치는 금수 자리입니다. 다른 위치를 선택하세요."
    }
}
