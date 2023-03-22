package domain.state

import domain.point.Point
import domain.point.Points
import domain.rule.OmokRule

class PlayingState(points: Points = Points()) : PlayerState(points) {
    override val isPlaying: Boolean = true
    override val isFoul: Boolean = false

    override fun add(newPoint: Point, otherPoints: Points, rule: OmokRule): PlayerState {
        val newStones = points.add(newPoint)
        val mine = points.getAll()
        val others = otherPoints.getAll()
        val isFoul = rule.checkAnyFoulCondition(mine, others, newPoint)

        if (rule.checkWin(mine, others, newPoint)) return WinState(newStones)
        if (isFoul.state) return FoulState(newStones)
        return PlayingState(newStones)
    }
}
