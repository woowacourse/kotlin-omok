package domain.rule

import domain.Stone
import domain.state.State

interface DetailRule {

    fun stateWillObeyThisRule(state: State, nextStone: Stone): Boolean
}
