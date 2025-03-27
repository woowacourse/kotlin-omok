package woowacourse.omok.model

import woowacourse.omok.model.position.Position
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

    fun play(
        position: Position,
        color: Color,
    ): MoveResult {
        val forbiddenMoveCheck: MoveResult = rule.checkForbiddenMove(board, position, color)
        if (forbiddenMoveCheck is MoveResult.Failure) return forbiddenMoveCheck
        when (val stoneAddResult: MoveResult = board.add(Stone(position, color))) {
            is MoveResult.Failure -> return stoneAddResult
            is MoveResult.Success -> {
                lastStone = Stone(position, color)
                return rule.checkWinCondition(board, position, color)
            }
        }
    }
}
