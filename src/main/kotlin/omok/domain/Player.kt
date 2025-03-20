package omok.domain

import rule.OmokRule
import rule.wrapper.point.Point

abstract class Player {
    protected abstract val stonesBacking: MutableList<Point>
    val stones: List<Point> get() = stonesBacking.toList()

    protected abstract val rule: OmokRule

    fun addStone(point: Point) {
        stonesBacking.add(point)
    }

    fun checkWin(startPoint: Point): Boolean {
        return rule.checkSerialSameStonesBiDirection(stonesBacking, startPoint, WIN_STANDARD)
    }

    abstract fun isViolation(
        otherStones: List<Point>,
        startPoint: Point,
    ): Boolean

    companion object {
        private const val WIN_STANDARD = 5
    }
}
