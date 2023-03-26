package domain.state

import domain.point.Point
import domain.point.Points
import domain.rule.OmokRule

abstract class PlayerState(protected val points: Points = Points()) {
    abstract val isPlaying: Boolean

    abstract val isFoul: Boolean

    abstract fun add(newPoint: Point, otherPoints: Points, rule: OmokRule): PlayerState

    fun hasStone(stone: Point): Boolean = points.hasStone(stone)

    fun getLastStone(): Point? = points.last

    fun getAllPoints(): Points = Points(points.getAll())
}
