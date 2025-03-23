package omok.domain.omokboard

import omok.domain.player.PlayerStone
import omok.domain.rule.OmokRule
import omok.domain.rule.PlaceResult

class PlayingBoard(
    val board: OmokBoard = OmokBoard.create(),
    private val rules: List<OmokRule>,
) {
    fun placeStone(playerStone: PlayerStone): PlaceResult = placeResult(playerStone)

    private fun placeResult(playerStone: PlayerStone): PlaceResult {
        var result: PlaceResult = PlaceResult.Success.Progress(playerStone)

        rules.forEach { rule ->
            result = rule.place(board, playerStone)
            if (result is PlaceResult.Failure || result is PlaceResult.Prohibition) return result
        }

        if (result is PlaceResult.Success) {
            board
                .find(playerStone.position)
                ?.updateState(playerStone.color)
        }

        return result
    }
}
