package omok.domain.omokboard

import omok.domain.placeresult.Failure
import omok.domain.placeresult.PlaceResult
import omok.domain.placeresult.Prohibition
import omok.domain.placeresult.Success
import omok.domain.player.PlayerStone
import omok.domain.rule.OmokRule

class PlayingBoard(
    val board: OmokBoard = OmokBoard.create(),
    private val rules: List<OmokRule>,
) {
    fun placeStone(playerStone: PlayerStone): PlaceResult = placeResult(playerStone)

    private fun placeResult(playerStone: PlayerStone): PlaceResult {
        var result: PlaceResult = Success.Progress(playerStone)

        rules.forEach { rule ->
            result = rule.place(board, playerStone)
            if (result is Failure || result is Prohibition) return result
        }

        if (result is Success) {
            board.updateBoard(playerStone)
        }

        return result
    }
}
