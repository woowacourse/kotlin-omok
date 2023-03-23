package domain.rule

import domain.state.State
import domain.stone.Stone

object RenjuRule {

    private val detailRules =
        listOf(BlackStonesAreNotAllowed33Rule, BlackStonesAreNotAllowed44Rule, BlackStonesAreNotAllowedLongMok)

    fun stateWillObeyThisRule(state: State, nextStone: Stone): Boolean =
        detailRules.all { it.stateWillObeyThisRule(state, nextStone) }
}
