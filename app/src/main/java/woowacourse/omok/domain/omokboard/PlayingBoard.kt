
package woowacourse.omok.domain.omokboard

import woowacourse.omok.domain.placeresult.GameOnGoing
import woowacourse.omok.domain.placeresult.PlaceResult
import woowacourse.omok.domain.player.PlayerStone
import woowacourse.omok.domain.player.StoneColor
import woowacourse.omok.domain.rule.OmokRules

class PlayingBoard(
    var board: OmokBoard = OmokBoard(),
    private val whiteRules: OmokRules,
    private val blackRules: OmokRules,
) {
    fun placeStone(playerStone: PlayerStone): PlaceResult = placeResult(playerStone)

    private fun placeResult(playerStone: PlayerStone): PlaceResult {
        val rules = if (playerStone.color == StoneColor.WHITE) whiteRules else blackRules
        val result = rules.place(board, playerStone)

        if (result is GameOnGoing) {
            board = board.updateBoard(playerStone)
        }

        return result
    }
}
