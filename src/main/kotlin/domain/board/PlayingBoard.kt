package domain.board

import domain.judgement.ForbiddenPositionChecker
import domain.judgement.WinningConditionChecker
import domain.stone.Color
import domain.stone.Position
import domain.stone.Stone

class PlayingBoard(
    placedStones: List<Stone> = listOf(),
    protected val winningConditionChecker: WinningConditionChecker,
    protected val forbiddenPositionChecker: ForbiddenPositionChecker
) : BasedBoard(placedStones.toList()) {

    override val winningColor: Color
        get() {
            throw IllegalStateException(PLAYING_GAME_ERROR)
        }

    override val isFinished: Boolean = false

    override fun isPossiblePut(position: Position): Boolean =
        !placedStones.any { stone -> stone.position == position }

    override fun addStone(stone: Stone): Board {
        if (isPossiblePut(stone.position).not()) return this
        return nextBoard(stone)
    }

    private fun nextBoard(newStone: Stone): Board {
        val nextStones = getStones() + newStone
        return when {
            isForbidden(newStone) -> FinishedBoard(nextStones, Color.WHITE, winningConditionChecker, forbiddenPositionChecker)
            isWin(newStone) -> FinishedBoard(nextStones, newStone.color, winningConditionChecker, forbiddenPositionChecker,)
            else -> PlayingBoard(nextStones, winningConditionChecker, forbiddenPositionChecker)
        }
    }

    private fun isForbidden(newStone: Stone): Boolean {
        return newStone.color == Color.BLACK && forbiddenPositionChecker.isForbidden(placedStones.toList(), newStone)
    }

    private fun isWin(newStone: Stone): Boolean {
        return winningConditionChecker.isWin(placedStones.toList(), newStone)
    }

    companion object {
        private const val PLAYING_GAME_ERROR = "[ERROR] 현재 게임이 진행중입니다."
    }
}
