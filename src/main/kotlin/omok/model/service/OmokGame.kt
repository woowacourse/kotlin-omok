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
        getPosition: (StoneColor) -> Position,
        onStonePlaced: (PlaceResult) -> Unit,
    ) {
        var stoneColor = StoneColor.BLACK

        while (true) {
            val playerStone = PlayerStone(stoneColor, getPosition(stoneColor))
            val placeResult = playingBoard.placeStone(playerStone)
            onStonePlaced(placeResult)

            when (placeResult) {
                is PlaceResult.Success.Finish -> break
                else -> {
                    stoneColor = stoneColor.reverse()
                    continue
                }
            }
        }
    }
}
