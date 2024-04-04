package woowacourse.omok.domain.controller

import woowacourse.omok.domain.model.Position
import woowacourse.omok.domain.model.StonePosition
import woowacourse.omok.domain.model.state.GameState
import woowacourse.omok.domain.model.state.InvalidPositionState

interface NextPositionListener {
    fun nextPosition(gameState: GameState): Position
    fun nextStonePositionCallback(gameState: GameState)
}

fun interface InvalidPositionHandler {
    fun handle(inValidStonePosition: StonePosition, invalidPositionState: InvalidPositionState)
}

fun interface FinishedObserver {
    fun finishedCallback(gameState: GameState)
}