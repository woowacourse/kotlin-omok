package domain.state

import domain.stone.Stones
import domain.rule.Referee

interface State {

    val stones: Stones
    fun put(point: Pair<Int, Int>, referee: Referee = Referee(listOf())): State
}
