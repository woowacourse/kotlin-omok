package domain

import domain.domain.Board
import domain.domain.Color
import domain.domain.Position
import domain.domain.Stone

class OmokGame(val board: Board) {
    fun getWinnerColor(showCurrentState: (Board) -> Unit, getPosition: () -> Position): Color {
        val stone = getStone(showCurrentState, getPosition)
        val winnerColor = judgeWinner(stone)
        board.placeStone(stone)
        return winnerColor ?: return getWinnerColor(showCurrentState, getPosition)
    }

    private fun getStone(showCurrentState: (Board) -> Unit, getPosition: () -> Position): Stone {
        showCurrentState(board)
        val stone = Stone(board.getCurrentTurn(), getPosition())
        if (!board.isEmpty(stone)) return getStone(showCurrentState, getPosition)
        return stone
    }

    private fun judgeWinner(stone: Stone): Color? {
        when {
            board.isBlackWin(stone) -> return Color.BLACK
            board.isWhiteWin(stone) -> return Color.WHITE
        }
        return null
    }
}
