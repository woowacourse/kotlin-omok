package woowacourse.omok.model

import woowacourse.omok.model.stone.Stone
import woowacourse.omok.model.stonestate.StoneState

sealed interface GameState {
    sealed interface LoadStone : GameState {
        data class Success(val stone: Stone) : LoadStone

        data class Failure(val throwable: Throwable) : LoadStone
    }

    sealed interface LoadStoneState : GameState {
        data class Success(val stoneState: StoneState) : LoadStoneState

        data class Failure(val throwable: Throwable) : LoadStoneState
    }

    sealed interface CheckRuleTypeState : GameState {
        data object Success : CheckRuleTypeState

        data class Failure(val throwable: Throwable) : CheckRuleTypeState
    }

    data object Finish : GameState
}
