package omok.domain.board

import omok.domain.point.Empty
import omok.domain.point.OmokPoints
import omok.domain.point.Point
import omok.domain.point.Protected
import omok.domain.rule.OmokRule
import omok.domain.rule.finder.Direction
import omok.view.BoardView

class OmokBoard(
    val omokPoints: OmokPoints,
    private val ruleChecker: OmokRule,
) {
    init {
        require(MAX_ROW_SIZE <= COLUMN_POOL.size) { ERROR_OUT_OF_COLUMN_POOL }
    }

    var latestStone: Point = Empty.dummy()
        private set

    fun toMatrix(): List<List<Point>> = omokPoints.toMatrix()

    fun isNotFull() = omokPoints.points.any { it is Empty }

    fun view(): BoardView = BoardView(this)

    fun pointValidation(point: Point) {
        require(!omokPoints.isOccupied(point)) { ERROR_OCCUPIED_POSITION }
        require(!omokPoints.isProtected(point)) { ERROR_PROTECTED_POSITION }
    }

    fun addStone(point: Point) {
        omokPoints.altStone(point)
        latestStone = point
        updateProtectedPlace()
    }

    fun goto(
        currentPosition: Point,
        direction: Direction,
    ): Point {
        val newX = currentPosition.x + direction.x
        val newY = currentPosition.y + direction.y
        return omokPoints.getPointAt(newY, newX)
    }

    private fun updateProtectedPlace() {
        omokPoints.points
            .filterIsInstance<Protected>()
            .forEach { point ->
                if (!ruleChecker.isProtected(point, this)) {
                    omokPoints.altStone(Empty(point.x, point.y))
                }
            }
        omokPoints.points
            .filter { it is Empty || it is Protected }
            .forEach { point ->
                if (ruleChecker.isProtected(point, this)) {
                    omokPoints.altStone(Protected(point.x, point.y))
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
