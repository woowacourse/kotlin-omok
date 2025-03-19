package omok.model.domain.omokboard

import omok.model.domain.player.PlayerStone
import omok.model.domain.rule.AlreadyExistRule
import omok.model.domain.rule.DrawRule
import omok.model.domain.rule.ExternalRule
import omok.model.domain.rule.InvalidPositionRule
import omok.model.domain.rule.OmokRule
import omok.model.domain.rule.PlaceResult
import omok.model.domain.rule.WinningRule
import rule.BlackRenjuRule

class PlayingBoard(
    val board: OmokBoard = OmokBoard.create(),
) {
    private val rules: List<OmokRule> =
        listOf(
            InvalidPositionRule(),
            AlreadyExistRule(),
            ExternalRule(BlackRenjuRule(board.width, board.height)),
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
        println(result)
        return result
    }
}
