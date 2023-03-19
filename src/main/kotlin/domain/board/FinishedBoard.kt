package domain.board

import domain.judgement.ForbiddenPositionChecker
import domain.judgement.WinningConditionChecker
import domain.stone.Color
import domain.stone.Position
import domain.stone.Stone

class FinishedBoard(
    placedStones: List<Stone>,
    override val winningColor: Color,
    protected val winningConditionChecker: WinningConditionChecker,
    protected val forbiddenPositionChecker: ForbiddenPositionChecker
) : BasedBoard(placedStones.toList()) {
    override val isFinished: Boolean = true

    init {
        when (winningColor) {
            Color.BLACK -> checkWinBlack()
            Color.WHITE -> checkWinWhite()
        }
    }

    private fun checkWinBlack() {
        check(placedStones.isNotEmpty()) {}
        val previousStones = placedStones.dropLast(1)
        val latestStone = placedStones.last()
        check(latestStone.color == winningColor) {}
        check(!forbiddenPositionChecker.isForbidden(previousStones, latestStone)) {}
        check(winningConditionChecker.isWin(previousStones, latestStone)) {}
    }

    private fun checkWinWhite() {
        check(placedStones.isNotEmpty()) {}
        val previousStones = placedStones.dropLast(1)
        val latestStone = placedStones.last()
        if (latestStone.color == winningColor) {
            check(winningConditionChecker.isWin(previousStones, latestStone)) { }
        } else {
            check(forbiddenPositionChecker.isForbidden(previousStones, latestStone)) { }
        }
    }

    override fun isPossiblePut(position: Position): Boolean = false

    override fun addStone(stone: Stone): Board = this
}
