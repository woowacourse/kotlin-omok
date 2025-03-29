package woowacourse.omok.model

import woowacourse.omok.model.rule.Rule

class Game(val board: Board, private val rule: Rule) {
    var lastStone: Stone? = null
        private set

    fun chooseTurn(): Color {
        return when (lastStone?.color) {
            Color.BLACK -> Color.WHITE
            Color.WHITE, null -> Color.BLACK
        }
    }

    fun play(newStone: Stone): MoveResult {
        val rangeCheck: MoveResult = board.checkRange(newStone)
        if (rangeCheck is MoveResult.Failure) return rangeCheck
        val forbiddenMoveCheck: MoveResult = rule.checkForbiddenMove(board, newStone)
        if (forbiddenMoveCheck is MoveResult.Failure) return forbiddenMoveCheck
        when (val stoneAddResult: MoveResult = board.add(newStone)) {
            is MoveResult.Failure -> return stoneAddResult
            is MoveResult.Success -> {
                lastStone = newStone
                return rule.checkWinCondition(board, newStone)
            }
        }
    }
}
