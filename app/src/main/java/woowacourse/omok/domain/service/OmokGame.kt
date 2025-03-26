package woowacourse.omok.domain.service

import woowacourse.omok.domain.omokboard.PlayingBoard
import woowacourse.omok.domain.omokboard.Position
import woowacourse.omok.domain.placeresult.GameFinish
import woowacourse.omok.domain.placeresult.GameOnGoing
import woowacourse.omok.domain.placeresult.InvalidMove
import woowacourse.omok.domain.placeresult.PlaceResult
import woowacourse.omok.domain.player.PlayerStone
import woowacourse.omok.domain.player.StoneColor

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
                is GameOnGoing -> {
                    stoneColor = stoneColor.reversed()
                    position = playerStone.position
                    continue
                }

                is GameFinish -> break

                is InvalidMove -> continue
            }
        }
    }
}
