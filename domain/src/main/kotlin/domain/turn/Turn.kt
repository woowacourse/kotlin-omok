package domain.turn

import domain.stone.Color
import domain.stone.Position
import domain.stone.Stone

class Turn(
    val color: Color,
    val boardState: BoardState
) {
    val winnerColor: Color?
        get() {
            if (boardState.isFinished()) return boardState.latestStone?.color
            return null
        }

    fun putStone(position: Position): Turn {
        val nextBoardState = boardState.putStone(Stone(position, color))
        if (nextBoardState === boardState) return this // 이미 놓여있어서 놓을 수 없는 경우
        return Turn(color.nextTurnColor(), nextBoardState)
    }

    private fun Color.nextTurnColor(): Color =
        when (this) {
            Color.BLACK -> Color.WHITE
            Color.WHITE -> Color.BLACK
        }
}
