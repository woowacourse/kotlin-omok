package woowacourse.omok.domain.service

import woowacourse.omok.domain.omokboard.PlayingBoard
import woowacourse.omok.domain.omokboard.Position
import woowacourse.omok.domain.placeresult.GameOnGoing
import woowacourse.omok.domain.placeresult.PlaceResult
import woowacourse.omok.domain.player.PlayerStone
import woowacourse.omok.domain.player.StoneColor

class OmokGame(
    private val playingBoard: PlayingBoard,
) {
    private var stoneColor = StoneColor.BLACK

    val currentStoneColor: StoneColor get() = stoneColor

    fun start(
        position: Position,
        onStonePlaced: (PlaceResult) -> Unit,
    ) {
        val playerStone = PlayerStone(stoneColor, position)
        val placeResult = playingBoard.placeStone(playerStone)
        onStonePlaced(placeResult)

        if (placeResult is GameOnGoing) stoneColor = stoneColor.reversed()
    }
}
