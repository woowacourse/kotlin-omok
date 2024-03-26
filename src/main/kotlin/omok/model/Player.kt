package omok.model

import omok.model.rule.GamePlayingRules
import omok.model.rule.ban.ForbiddenPlace

class Player(val stone: Stone, private val forbiddenPlaces: List<ForbiddenPlace> = listOf()) {
    fun canPlace(
        board: Board,
        position: Position,
    ): Boolean {
        return forbiddenPlaces.all { it.availablePosition(board, position) }
    }
}

class Player2(val stone: Stone, private val gamePlayingRules: GamePlayingRules) {
    fun canPlace(
        board: Board,
        position: Position,
    ): Boolean = gamePlayingRules.availablePosition(board, position)
}
