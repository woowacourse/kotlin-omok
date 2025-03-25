package omok.domain.omokboard

import omok.domain.placeresult.GameOnGoing
import omok.domain.placeresult.InvalidMove
import omok.domain.placeresult.PlaceResult
import omok.domain.player.PlayerStone
import omok.domain.rule.OmokRule

class PlayingBoard(
    var board: OmokBoard = OmokBoard.create(),
    private val rules: List<OmokRule>,
) {
    fun placeStone(playerStone: PlayerStone): PlaceResult = placeResult(playerStone)

    private fun placeResult(playerStone: PlayerStone): PlaceResult {
        var result: PlaceResult = GameOnGoing

        rules.forEach { rule ->
            result = rule.place(board, playerStone)
            if (result is InvalidMove) return result
        }

        if (result is GameOnGoing) {
            board = board.updateBoard(playerStone)
        }

        return result
    }
}
