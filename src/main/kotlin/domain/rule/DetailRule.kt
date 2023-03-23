package domain.rule

import domain.state.State
import domain.stone.Stone

interface DetailRule {

    fun stateWillObeyThisRule(state: State, nextStone: Stone): Boolean
}
