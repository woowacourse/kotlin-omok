package omok.domain.omokboard

import omok.domain.placeresult.GameNotProgress
import omok.domain.placeresult.GameProgress
import omok.domain.placeresult.PlaceResult
import omok.domain.player.PlayerStone
import omok.domain.rule.OmokRule

class PlayingBoard(
    val board: OmokBoard = OmokBoard.create(),
    private val rules: List<OmokRule>,
) {
    fun placeStone(playerStone: PlayerStone): PlaceResult = placeResult(playerStone)

    private fun placeResult(playerStone: PlayerStone): PlaceResult {
        var result: PlaceResult = GameProgress()

        rules.forEach { rule ->
            result = rule.place(board, playerStone)
            if (result is GameNotProgress) return result
        }

        if (result is GameProgress) {
            board.updateBoard(playerStone)
        }

        return result
    }
}
