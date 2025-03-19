package omok.model.domain.omokboard

import omok.model.domain.player.PlayerStone
import omok.model.domain.rule.AlreadyExistRule
import omok.model.domain.rule.PlaceResult

class PlayingBoard(
    val board: OmokBoard = OmokBoard.create(),
) {
    fun placeStone(playerStone: PlayerStone): PlaceResult = placeResult(playerStone)

    private fun placeResult(playerStone: PlayerStone): PlaceResult {
        when (val result = AlreadyExistRule().canPlace(board, playerStone)) {
            PlaceResult.Success.Progress(playerStone) -> {
                board
                    .find(playerStone.position)
                    ?.updateState(playerStone.color)
                return result
            }

            else -> return result
        }
    }
}
