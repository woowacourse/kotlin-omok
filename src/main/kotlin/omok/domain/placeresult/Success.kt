package omok.domain.placeresult

import omok.domain.player.PlayerStone
import omok.domain.rule.GameResult

sealed class Success : PlaceResult {
    data class Progress(
        val lastStone: PlayerStone,
    ) : Success()

    data class Finish(
        val gameResult: GameResult,
    ) : Success()
}
