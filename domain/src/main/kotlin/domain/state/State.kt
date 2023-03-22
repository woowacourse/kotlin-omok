package domain.state

import domain.stone.Point
import domain.stone.Stones
import domain.rule.Referee

interface State {

    val stones: Stones
    fun put(point: Point, referee: Referee = Referee(listOf())): State
}
