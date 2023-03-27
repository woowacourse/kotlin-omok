package domain.state

import domain.stone.Stones
import domain.rule.Referee
import domain.stone.Point

interface State {

    val stones: Stones
    fun put(point: Point, referee: Referee = Referee(listOf())): State
}
