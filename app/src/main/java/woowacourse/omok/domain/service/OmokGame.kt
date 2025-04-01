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
    var stoneColor = StoneColor.BLACK

    fun start(
        position: Position,
        onStonePlaced: (PlaceResult) -> Unit,
    ) {
        val playerStone = PlayerStone(this.stoneColor, position)
        val placeResult = playingBoard.placeStone(playerStone)
        onStonePlaced(placeResult)

        if (placeResult is GameOnGoing) this.stoneColor = this.stoneColor.reversed()
    }
}
