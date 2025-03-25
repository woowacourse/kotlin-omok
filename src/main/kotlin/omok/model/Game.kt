package omok.model

import omok.model.position.Position
import omok.model.rule.Rule

class Game(val board: Board, private val rule: Rule) {
    var lastStone: Stone? = null
        private set

    fun chooseTurn(): Color {
        return when (lastStone?.color) {
            Color.BLACK -> Color.WHITE
            Color.WHITE, null -> Color.BLACK
        }
    }

    fun processTurn(
        position: Position,
        color: Color,
    ): MoveResult {
        (rule.checkViolation(board, position, color) as? MoveResult.Failure)?.let { moveResult -> return moveResult }
        (board.add(Stone(position, color)) as? MoveResult.Failure)?.let { moveResult -> return moveResult }
        lastStone = Stone(position, color)
        return rule.checkOmok(board, position, color)
    }
}
