package omok.domain

import rule.OmokRule
import rule.type.Violation
import rule.wrapper.point.Point

class OmokGrid(board: List<MutableList<StoneState>> = List(DEFAULT_SIZE) { MutableList(DEFAULT_SIZE) { StoneState.BLANK } }) {
    private val _board: List<MutableList<StoneState>> = board.deepCopy()
    val board: List<MutableList<StoneState>>
        get() = _board.deepCopy()

    fun putStone(
        point: Point,
        state: StoneState,
    ) {
        if (_board[point.row][point.col] != StoneState.BLANK) throw IllegalStateException(ERROR_STONE_ALREADY_PUT)
        _board[point.row][point.col] = state
    }

    fun isFull(): Boolean {
        return _board.all { row -> row.all { it != StoneState.BLANK } }
    }

    fun isViolation(
        omokRule: OmokRule,
        startPoint: Point,
    ): Boolean {
        val violation =
            omokRule.checkAnyFoulCondition(
                findStones(StoneState.BLACK),
                findStones(StoneState.WHITE),
                startPoint,
            )

        return violation != Violation.NONE
    }

    fun findStones(state: StoneState): List<Point> {
        return _board.flatMapIndexed { rowIndex, stateList ->
            stateList.withIndex()
                .filter { it.value == state }
                .map { Point(rowIndex, it.index) }
        }
    }

    companion object {
        const val DEFAULT_SIZE: Int = 15
        private const val ERROR_STONE_ALREADY_PUT = "이미 돌이 있습니다."
    }
}

fun List<MutableList<StoneState>>.deepCopy(): List<MutableList<StoneState>> = map { it.toMutableList() }.toList()
