package omok.model.service

import omok.model.domain.omokboard.PlayingBoard
import omok.model.domain.omokboard.Position
import omok.model.domain.player.PlayerStone
import omok.model.domain.player.StoneColor
import omok.model.domain.rule.PlaceResult

class OmokGame(
    private val playingBoard: PlayingBoard,
) {
    fun start(
        getPosition: (StoneColor, Position?) -> Position,
        onStonePlaced: (PlaceResult) -> Unit,
    ) {
        var stoneColor = StoneColor.BLACK
        var position: Position? = null

        while (true) {
            val playerStone = PlayerStone(stoneColor, getPosition(stoneColor, position))
            val placeResult = playingBoard.placeStone(playerStone)
            onStonePlaced(placeResult)

            when (placeResult) {
                is PlaceResult.Success.Progress -> {
                    stoneColor = stoneColor.reversed()
                    position = playerStone.position
                    continue
                }

                is PlaceResult.Success.Finish -> break

                is PlaceResult.Failure -> continue
            }
        }
    }
}
