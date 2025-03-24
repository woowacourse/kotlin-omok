package omok.domain.omokboard

import omok.domain.player.PlayerStone
import omok.domain.rule.place.AlreadyExistStoneRule
import omok.domain.rule.place.DrawRule
import omok.domain.rule.place.ExternalRule
import omok.domain.rule.place.InvalidPositionRule
import omok.domain.rule.place.PlaceResult
import omok.domain.rule.place.PlaceRule
import omok.domain.rule.winning.WinningRule

class PlayingBoard(
    val board: OmokBoard = OmokBoard.create(),
) {
    private val rules: List<PlaceRule> =
        listOf(
            InvalidPositionRule(),
            AlreadyExistStoneRule(),
            ExternalRule(),
            DrawRule(),
            WinningRule(),
        )

    fun placeStone(playerStone: PlayerStone): PlaceResult = placeResult(playerStone)

    private fun placeResult(playerStone: PlayerStone): PlaceResult {
        var result: PlaceResult = PlaceResult.Success.Progress(playerStone)

        rules.forEach { rule ->
            result = rule.canPlace(board, playerStone)
            if (result is PlaceResult.Failure) return result
        }

        if (result is PlaceResult.Success) {
            board
                .find(playerStone.position)
                ?.updateState(playerStone.color)
        }

        return result
    }
}
