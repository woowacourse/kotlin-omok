package omok.domain.service

import omok.domain.omokboard.PlayingBoard
import omok.domain.omokboard.Position
import omok.domain.placeresult.Failure
import omok.domain.placeresult.PlaceResult
import omok.domain.placeresult.Success
import omok.domain.player.PlayerStone
import omok.domain.player.StoneColor

class OmokGame(
    private val playingBoard: PlayingBoard,
) {
    fun start(
        getNewPosition: (StoneColor, Position?) -> Position,
        onStonePlaced: (PlaceResult) -> Unit,
    ) {
        var stoneColor = StoneColor.BLACK
        var position: Position? = null

        while (true) {
            val playerStone = PlayerStone(stoneColor, getNewPosition(stoneColor, position))
            val placeResult = playingBoard.placeStone(playerStone)
            onStonePlaced(placeResult)

            when (placeResult) {
                is Success.Progress -> {
                    stoneColor = stoneColor.reversed()
                    position = playerStone.position
                    continue
                }

                is Success.Finish -> break

                is Failure -> continue
            }
        }
    }
}
