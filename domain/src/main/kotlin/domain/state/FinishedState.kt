package domain.state

import domain.point.Point
import domain.point.Points
import domain.rule.OmokRule

abstract class FinishedState(points: Points = Points()) : PlayerState(points) {
    override val isPlaying: Boolean = false

    override fun add(newPoint: Point, otherPoints: Points, rule: OmokRule): PlayerState = this
}
