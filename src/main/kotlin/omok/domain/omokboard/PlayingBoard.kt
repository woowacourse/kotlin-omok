package omok.domain.omokboard

import omok.domain.player.PlayerStone
import omok.domain.rule.AlreadyExistStoneRule
import omok.domain.rule.DrawRule
import omok.domain.rule.ExternalRule
import omok.domain.rule.InvalidPositionRule
import omok.domain.rule.OmokRule
import omok.domain.rule.PlaceResult
import omok.domain.rule.WinningRule

class PlayingBoard(
    val board: OmokBoard = OmokBoard.create(),
) {
    private val rules: List<OmokRule> =
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
