package woowacourse.omok.model

import woowacourse.omok.model.state.Black
import woowacourse.omok.model.state.GameState
import woowacourse.omok.model.state.TurnState
import woowacourse.omok.model.state.White

class Board(
    private val placementInfo: PlacementInfo = PlacementInfo(),
) {
    val status: List<List<Color?>>
        get() = placementInfo.status.map { it.toList() }.toList()

    private val placementCount: Int
        get() = status.flatten().count { it != null }

    var lastPlacement: Stone? = null
        private set

    private val turnState: TurnState
        get() = if (isEven(placementCount)) Black(placementInfo.status) else White(placementInfo.status)

    fun place(position: Position): GameState {
        if (position.horizontalCoordinate.index !in MIN_INDEX..MAX_INDEX) return GameState.Error(message = MESSAGE_WRONG_ROW_RANGE)
        if (position.verticalCoordinate.index !in MIN_INDEX..MAX_INDEX) return GameState.Error(message = MESSAGE_WRONG_COL_RANGE)
        if (status[COMPUTATION_BOARD_SIZE - position.horizontalCoordinate.index][position.verticalCoordinate.index] != null) {
            return GameState.Error(
                message = MESSAGE_DUPLICATED_POSITION,
            )
        }
        if (placementCount >= DISPLAY_BOARD_SIZE * DISPLAY_BOARD_SIZE) return GameState.GameOver(gameResult = GameResult.DRAW)

        lastPlacement = getLastPlacementInfo(position)
        val turnResult = turnState.getWinningResult(position, placementInfo::markSinglePlace)
        return turnResult?.let { GameState.GameOver(turnResult) } ?: GameState.OnProgress
    }

    private fun getLastPlacementInfo(position: Position) =
        when (turnState) {
            is Black -> Stone.Black(position)
            else -> Stone.White(position)
        }

    private fun isEven(num: Int): Boolean {
        return num % ODD_EVEN_INDICATOR == 0
    }

    companion object {
        private const val MESSAGE_DUPLICATED_POSITION = "중복된 곳에 착수할 수 없습니다."
        private const val ODD_EVEN_INDICATOR = 2
        private const val COMPUTATION_BOARD_SIZE = 16
        private const val DISPLAY_BOARD_SIZE = COMPUTATION_BOARD_SIZE - 1
        private const val MIN_INDEX = 1
        private const val MAX_INDEX = 15
        private const val MIN_COL = 'A'
        private const val MAX_COL = 'O'
        private const val MESSAGE_WRONG_ROW_RANGE = "$MIN_INDEX 부터 ${MAX_INDEX}사이의 정수를 입력해야 합니다"
        private const val MESSAGE_WRONG_COL_RANGE = "$MIN_COL 부터 ${MAX_COL}사이의 알파벳을 입력해야 합니다"
    }
}
