package woowacourse.omok.domain.omokboard

import woowacourse.omok.domain.placeresult.GameOnGoing
import woowacourse.omok.domain.placeresult.InvalidMove
import woowacourse.omok.domain.placeresult.PlaceResult
import woowacourse.omok.domain.player.PlayerStone
import woowacourse.omok.domain.rule.OmokRule

class PlayingBoard(
    var board: OmokBoard = OmokBoard(),
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
