package omok.model

import omok.model.rule.GamePlayingRules
import omok.model.rule.RuleAdapter2

class Player2(val stone: Stone, private val rules: RuleAdapter2) {
    fun canPlace(
        board: Board,
        position: Position,
    ): Boolean = rules.validPosition(board, position)

    fun isWin(
        board: Board,
        position: Position,
    ): Boolean = rules.isWin(board, position)
}

class Player(val stone: Stone, private val gamePlayingRules: GamePlayingRules) {
    fun canPlace(
        board: Board,
        position: Position,
    ): Boolean = gamePlayingRules.availablePosition(board, position)

    fun isWin(
        board: Board,
        position: Position,
    ): Boolean {
        return gamePlayingRules.isWin(board, position)
    }
}
