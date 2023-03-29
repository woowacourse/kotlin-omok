package domain.domain.state

import domain.CoordinateState
import domain.CoordinateState.BLACK
import domain.CoordinateState.WHITE
import domain.Position
import domain.domain.Stones
import domain.library.Rule
import domain.library.ark.ArkRuleAdapter

abstract class State(val stones: Stones) {
    protected val rule: Rule = ArkRuleAdapter()
    abstract fun toNextState(position: Position): State
    fun getLastPosition(): Position? = stones.lastPosition
    fun getTurn(): CoordinateState {
        return when (this) {
            is BlackTurn -> BLACK
            is WhiteTurn -> WHITE
            is BlackWin -> BLACK
            is WhiteWin -> WHITE
            else -> throw IllegalStateException("예상되지 않은 상태입니다.")
        }
    }
}
