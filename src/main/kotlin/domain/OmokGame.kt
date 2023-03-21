package domain

import domain.domain.Board2
import domain.domain.Color
import domain.domain.Position2
import domain.domain.Stone

class OmokGame(val board: Board2) {
    fun getWinnerColor(showCurrentState: (Board2) -> Unit, getPosition: () -> Position2): Color {
        val stone = getStone(showCurrentState, getPosition)
        val winnerColor = judgeWinner(stone)
        board.placeStone(stone)
        return winnerColor ?: return getWinnerColor(showCurrentState, getPosition)
    }

    private fun getStone(showCurrentState: (Board2) -> Unit, getPosition: () -> Position2): Stone {
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
