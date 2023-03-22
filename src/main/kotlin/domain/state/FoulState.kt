package domain.state

import domain.point.Points

class FoulState(points: Points = Points()) : FinishedState(points) {
    override val isFoul: Boolean = true
}
