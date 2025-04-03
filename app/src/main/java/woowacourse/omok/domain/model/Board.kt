package woowacourse.omok.domain.model

import woowacourse.omok.domain.model.state.PlayResult
import woowacourse.omok.domain.model.state.State
import woowacourse.omok.domain.model.stone.BlackStones
import woowacourse.omok.domain.model.stone.StoneColor
import woowacourse.omok.domain.model.stone.WhiteStones

class Board(
    state: State = State.Playing(BlackStones(), WhiteStones(), StoneColor.BLACK),
    val size: Int = DEFAULT_BOARD_SIZE,
) {
    init {
        require(size >= MINIMUM_BOARD_SIZE) { ERROR_INVALID_BOARD_SIZE }
    }

    var state = state
        private set

    fun playOmok(
        onTurn: (StoneColor, Point?) -> Unit,
        onPointInput: () -> Point,
        onBoardUpdated: (BlackStones, WhiteStones) -> Unit,
    ) {
        while (state is State.Playing) {
            val currentState = state as State.Playing

            onTurn(
                currentState.nextStoneColor,
                if (currentState.nextStoneColor == StoneColor.BLACK) {
                    currentState.whiteStones.lastStonePoint
                } else {
                    currentState.blackStones.lastStonePoint
                },
            )

            val point = onPointInput()
            state = place(currentState, point, size, onBoardUpdated)
        }
    }

    private fun place(
        currentState: State.Playing,
        point: Point,
        boardSize: Int,
        onBoardUpdated: (BlackStones, WhiteStones) -> Unit,
    ): State {
        val (newBlackStones, newWhiteStones) =
            when (currentState.nextStoneColor) {
                StoneColor.BLACK -> currentState.blackStones + point to currentState.whiteStones
                StoneColor.WHITE -> currentState.blackStones to currentState.whiteStones + point
            }

        val result =
            when {
                currentState.blackStones.isOmok(point) -> PlayResult.Omok(StoneColor.BLACK)
                currentState.whiteStones.isOmok(point) -> PlayResult.Omok(StoneColor.WHITE)
                newBlackStones.points.size + newWhiteStones.points.size >= boardSize * boardSize -> PlayResult.Draw
                currentState.blackStones.isDoubleThreeFoul(newWhiteStones, point) -> PlayResult.Foul.DoubleThree
                currentState.blackStones.isDoubleFourFoul(newWhiteStones, point) -> PlayResult.Foul.DoubleFour
                currentState.blackStones.isOverLine(point) -> PlayResult.Foul.OverLine
                currentState.blackStones.contains(point) || currentState.whiteStones.contains(point) -> PlayResult.Foul.Duplicated
                else ->
                    PlayResult.Continue(
                        State.Playing(
                            newBlackStones,
                            newWhiteStones,
                            if (currentState.nextStoneColor == StoneColor.BLACK) StoneColor.WHITE else StoneColor.BLACK,
                        ),
                    )
            }

        if (result is PlayResult.Continue) {
            onBoardUpdated(newBlackStones, newWhiteStones)
        }

        return when (result) {
            is PlayResult.Omok -> State.Finished(result.winner)
            is PlayResult.Draw -> State.Finished(null)
            is PlayResult.Continue -> result.nextState
            else -> currentState
        }
    }

    companion object {
        const val DEFAULT_BOARD_SIZE = 15
        private const val MINIMUM_BOARD_SIZE = 5
        private const val ERROR_INVALID_BOARD_SIZE = "[ERROR] 오목판의 사이즈는 최소 5x5이어야 합니다."
    }
}
