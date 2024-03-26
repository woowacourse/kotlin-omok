package omok.model

import omok.model.rule.GamePlayingRules

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
