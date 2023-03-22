package domain.state

import domain.point.Points

class WinState(points: Points = Points()) : FinishedState(points) {
    override val isFoul: Boolean = false
}
