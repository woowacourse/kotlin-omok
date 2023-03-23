package domain.turn

import domain.stone.Color
import domain.stone.Position
import domain.stone.Stone

class Turn(
    val color: Color,
    val boardState: BoardState
) {
    init {
        if (boardState.latestStone != null) {
            check(boardState.latestStone.color.nextTurnColor() == color) { ERROR_LATEST_COLOR_IS_NOT_MATCH_CUR_COLOR }
        } else {
            check(Color.BLACK == color) { ERROR_FIRST_TURN_IS_NOT_BLACK }
        }
    }

    val winnerColor: Color?
        get() {
            if (boardState.isFinished()) return boardState.latestStone?.color
            return null
        }

    fun putStone(position: Position): Turn {
        val newBoardState = boardState.putStone(Stone(position, color))
        if (newBoardState === boardState) return this // 이미 놓여있어서 놓을 수 없는 경우
        return Turn(color.nextTurnColor(), newBoardState)
    }

    private fun Color.nextTurnColor(): Color =
        when (this) {
            Color.BLACK -> Color.WHITE
            Color.WHITE -> Color.BLACK
        }

    companion object {
        private const val ERROR_FIRST_TURN_IS_NOT_BLACK = "[ERROR] 첫 번째 턴은 검은색이여야 합니다."
        private const val ERROR_LATEST_COLOR_IS_NOT_MATCH_CUR_COLOR =
            "[ERROR] 이번 턴의 색상이 마지막에 놓은 돌의 다음 턴 색상이 아닙니다."
    }
}
